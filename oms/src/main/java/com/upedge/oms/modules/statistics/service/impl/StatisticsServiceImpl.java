package com.upedge.oms.modules.statistics.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.order.request.OrderDailyCountRequest;
import com.upedge.common.model.order.vo.OrderDailyCountVo;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductVariantTo;
import com.upedge.common.model.user.request.CustomerByIdsRequest;
import com.upedge.common.model.user.request.OrderBenefitsRequest;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.OrderBenefitsVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateTools;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderRefundDao;
import com.upedge.oms.modules.order.dao.StoreOrderDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.service.StoreOrderService;
import com.upedge.oms.modules.pack.entity.PackageUsdRate;
import com.upedge.oms.modules.pack.service.PackageInfoService;
import com.upedge.oms.modules.pack.service.PackageUsdRateService;
import com.upedge.oms.modules.pack.vo.EchartsPie;
import com.upedge.oms.modules.pack.vo.PackageData;
import com.upedge.oms.modules.statistics.dto.DashboardOrderDto;
import com.upedge.oms.modules.statistics.dto.WaitTrackOrderData;
import com.upedge.oms.modules.statistics.dto.WaitTrackOrderDataDetails;
import com.upedge.oms.modules.statistics.request.*;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.statistics.service.StatisticsService;
import com.upedge.oms.modules.statistics.vo.*;
import com.upedge.oms.modules.stock.dao.StockOrderDao;
import com.upedge.oms.modules.stock.dao.StockOrderRefundDao;
import com.upedge.oms.modules.tickets.dao.SupportTicketsDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleRefundDao;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiGetOrderSourceResponse;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiOrderSource;
import com.upedge.thirdparty.saihe.modules.source.SaiheSourceApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderRefundDao orderRefundDao;
    @Autowired
    private StockOrderDao stockOrderDao;
    @Autowired
    private StockOrderRefundDao stockOrderRefundDao;
    @Autowired
    private WholesaleOrderDao wholesaleOrderDao;
    @Autowired
    private WholesaleRefundDao wholesaleRefundDao;
    @Autowired
    private PmsFeignClient pmsFeignClient;
    @Autowired
    ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    StoreOrderDao storeOrderDao;
    @Autowired
    SupportTicketsDao supportTicketsDao;
    @Autowired
    UmsFeignClient umsFeignClient;
    @Autowired
    private StoreOrderService storeOrderService;
    @Autowired
    private PackageInfoService packageInfoService;
    @Autowired
    private PackageUsdRateService packageUsdRateService;
    @Autowired
    private OrderDailyPayCountService orderDailyPayCountService;


    @Override
    public List<CustomerOrderCostVo> selectCustomerOrderCost(Session session) {
        return null;
    }

    @Override
    public OrderStateCountVo countOrderState(Session session) {
        OrderStateCountVo orderStateCountVo = new OrderStateCountVo();

        Page<Order> page = new Page<>();
        Order order = new Order();
        order.setCustomerId(session.getCustomerId());
        order.setPayState(OrderConstant.PAY_STATE_PAID);
        page.setT(order);
        long paidOrderCount = orderDao.count(page);

        order.setPayState(null);
        page.setCondition("refund_state > 1");
        long refundOrderCount = orderDao.count(page);

        orderStateCountVo.setPaidOrderCount(paidOrderCount);
        orderStateCountVo.setRefundOrderCount(refundOrderCount);
        return orderStateCountVo;
    }

    /**
     * 订单导出页 订单相关数据统计
     *
     * @param session
     * @return
     */
    @Override
    public BaseResponse orderHandleData(Session session) {
        //admin登录用户id 客户经理
        String managerCode = null;
        OrderHandleDataVo orderHandleDataVo = new OrderHandleDataVo();
        //普通用户

        //普通订单退款
        long normalHandleRefundNum = orderRefundDao.orderHandleRefundData(managerCode);
        orderHandleDataVo.setNormalOrderRefundReview(normalHandleRefundNum);
        //普通订单未导入赛盒
        long normalHandleImportNum = orderDao.orderHandleImportData(managerCode);
        orderHandleDataVo.setNormalOrderUnImportSaihe(normalHandleImportNum);
        //从redis里面获取
        Map<Object, Object> map1 = redisTemplate.opsForHash().entries(RedisKey.HASH_BAD_NORMAL_ORDER_PRODUCT);
        //Map map2=redisTemplate.opsForHash().entries(RedisKey.HASH_BAD_WHOLESALE_ORDER_PRODUCT);
        //普通订单未导入产品
        long normalHandleProductNum = map1.size();
        orderHandleDataVo.setNormalOrderProductUnImportSaihe(normalHandleProductNum);
        List<NormalHandleProductVo> normalHandleProductVoList = new ArrayList<>();
        for (Map.Entry entry : map1.entrySet()) {
            NormalHandleProductVo normalHandleProductVo = new NormalHandleProductVo();
            normalHandleProductVo.setProductId(String.valueOf(entry.getKey()));
            normalHandleProductVo.setAdminUser(String.valueOf(entry.getValue()));
            normalHandleProductVoList.add(normalHandleProductVo);
        }
        orderHandleDataVo.setManagerUnImportProductsVos(normalHandleProductVoList);
        //普通订单补发待审核
        long normalHandleReshipNum = orderDao.orderHandleReshipData(managerCode);
        orderHandleDataVo.setNormalReshipOrderPendingReview(normalHandleReshipNum);
        //普通订单未发货订单数
        long normalWaitTrackNum = orderDao.normalWaitTrackNum(managerCode);
        orderHandleDataVo.setNormalOrderNotShipped(normalWaitTrackNum);
        //备库订单待处理
        long stockHandleImportNum = stockOrderDao.orderHandleImportData(managerCode);
        orderHandleDataVo.setStockOrderPendingReview(stockHandleImportNum);
        //备库退款
        long stockHandleRefundNum = stockOrderRefundDao.stockHandleRefundData(managerCode);
        orderHandleDataVo.setStockOrderRefundReview(stockHandleRefundNum);
        //批发订单退款
        long wholesaleHandleRefundNum = wholesaleRefundDao.wholesaleHandleRefundData(managerCode);
        orderHandleDataVo.setWholesaleOrderRefundReview(wholesaleHandleRefundNum);
        //批发订单未导入赛盒
        long wholesaleHandleImportNum = wholesaleOrderDao.wholesaleHandleImportData(managerCode);
        orderHandleDataVo.setWholesaleOrderUnImportSaihe(wholesaleHandleImportNum);
        //批发订单补发待审核
        long wholesaleHandleReshipNum = wholesaleOrderDao.wholesaleHandleReshipData(managerCode);
        orderHandleDataVo.setWholesaleReshipOrderPendingReview(wholesaleHandleReshipNum);
        //批发订单未发货订单数
        long wholesaleWaitTrackNum = wholesaleOrderDao.wholesaleWaitTrackNum(managerCode);
        orderHandleDataVo.setWholesaleOrderNotShipped(wholesaleWaitTrackNum);
        //未读tickets
        long unreadMsgNum = 0;
        unreadMsgNum = supportTicketsDao.unreadMsgNum(managerCode);
        orderHandleDataVo.setUnReadTicketMessageCount(unreadMsgNum);
        //待处理tickets
        long handleTicketNum = supportTicketsDao.handleTicketNum(managerCode);
        ;
        orderHandleDataVo.setTicketPendingReview(handleTicketNum);
        return new BaseResponse(ResultCode.SUCCESS_CODE, orderHandleDataVo);

    }

    /**
     * 未付款普通订单SKU统计
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse toOrderSkuData(ToOrderSkuDataRequest request) {
        request.initFromNum();
        List<ToOrderSkuVo> results = orderDao.selectToOrderSkuData(request);
        Long total = orderDao.countToOrderSkuData(request);
        request.setTotal(total);
        List<Long> variantIds = new ArrayList<>();
        results.forEach(toOrderSkuVo -> {
            variantIds.add(toOrderSkuVo.getAdminVariantId());
        });

        if (variantIds.size() > 0) {
            ListVariantsRequest r = new ListVariantsRequest();
            r.setVariantIds(variantIds);
            BaseResponse response = pmsFeignClient.listVariantByIds(r);
            if (response.getCode() == 0) {
                return response;
            }
            response.getData();
            Map<Long, ProductVariantTo> variantDetailMap = new HashMap<>();
            List<LinkedHashMap> variantDetailList = (List<LinkedHashMap>) response.getData();
            variantDetailList.forEach(v -> {
                ProductVariantTo variantDetail = JSON.parseObject(JSON.toJSONString(v), ProductVariantTo.class);
                variantDetailMap.put(variantDetail.getId(), variantDetail);
            });
            results.forEach(toOrderSkuVo -> {
                ProductVariantTo variantDetail = variantDetailMap.get(toOrderSkuVo.getAdminVariantId());
                if (null != variantDetail) {
                    toOrderSkuVo.setAdminVariantName(variantDetail.getCnName());
                }
            });
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results);
    }

    /**
     * 单个客户订单数据统计
     *
     * @param customerId
     * @return
     */
    @Override
    public BaseResponse customerOrderData(Long customerId) {

        Map<String, Object> map = new HashMap<>();
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            //普通订单
            //已支付订单总数  已支付订单已发货数 已支付订单未发货数
            long normalOrderPaidTotalNum = orderDao.normalOrderPaidTotalNum(customerId);
            map.put("normalOrderPaidTotalNum", normalOrderPaidTotalNum);
            long normalOrderPaidShipYesNum = orderDao.normalOrderPaidShipYesNum(customerId);
            map.put("normalOrderPaidShipYesNum", normalOrderPaidShipYesNum);
            long normalOrderPaidShipNoNum = orderDao.normalOrderPaidShipNoNum(customerId);
            map.put("normalOrderPaidShipNoNum", normalOrderPaidShipNoNum);
        }, threadPoolExecutor);

        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            //普通订单
            //待支付订单数 已取消订单数
            long normalOrderToPayNum = orderDao.normalOrderToPayNum(customerId);
            map.put("normalOrderToPayNum", normalOrderToPayNum);
            long normalOrderCancelNum = orderDao.normalOrderCancelNum(customerId);
            map.put("normalOrderCancelNum", normalOrderCancelNum);
            //已支付总金额
            BigDecimal normalOrderPaidAmount = orderDao.normalOrderPaidAmount(customerId);
            map.put("normalOrderPaidAmount", normalOrderPaidAmount);
        }, threadPoolExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            //批发订单
            //已支付订单总数  已支付订单已发货数 已支付订单未发货数
            long wholesaleOrderPaidTotalNum = wholesaleOrderDao.wholesaleOrderPaidTotalNum(customerId);
            map.put("wholesaleOrderPaidTotalNum", wholesaleOrderPaidTotalNum);
            long wholesaleOrderPaidShipYesNum = wholesaleOrderDao.wholesaleOrderPaidShipYesNum(customerId);
            map.put("wholesaleOrderPaidShipYesNum", wholesaleOrderPaidShipYesNum);
            long wholesaleOrderPaidShipNoNum = wholesaleOrderDao.wholesaleOrderPaidShipNoNum(customerId);
            map.put("wholesaleOrderPaidShipNoNum", wholesaleOrderPaidShipNoNum);
        }, threadPoolExecutor);

        CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
            //批发订单
            //待支付订单数 已取消订单数
            long wholesaleOrderToPayNum = wholesaleOrderDao.wholesaleOrderToPayNum(customerId);
            map.put("wholesaleOrderToPayNum", wholesaleOrderToPayNum);
            long wholesaleOrderCancelNum = wholesaleOrderDao.wholesaleOrderCancelNum(customerId);
            map.put("wholesaleOrderCancelNum", wholesaleOrderCancelNum);
            BigDecimal wholesaleOrderPaidAmount = wholesaleOrderDao.wholesaleOrderPaidAmount(customerId);
            map.put("wholesaleOrderPaidAmount", wholesaleOrderPaidAmount);
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(future1, future2, future3, future4).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BaseResponse(ResultCode.SUCCESS_CODE, map);
    }

    /**
     * 已支付订单统计
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse customerOrderPayCount(OrderPayCountRequest request) {
        request.initDateRange();
        Map<String, List<OrderPayCountVo>> map = new HashMap<>();

        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            List<OrderPayCountVo> days = orderDao.countOrderPayByDay(request);
            map.put("days", days);
        }, threadPoolExecutor);
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            List<OrderPayCountVo> mouths = orderDao.countOrderPayByMonth(request);
            map.put("mouths", mouths);
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(future1, future2).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, map, request);
    }

    /**
     * 仪表盘数据统计
     *
     * @param session
     * @return
     */
    @Override
    public BaseResponse dashboardOrderData(Session session) {
        Map<String, Object> map = new HashMap<>();
        String manager = null;
        //普通用户

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String userManager = manager;
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            OrderDailyCountRequest request = new OrderDailyCountRequest();
            request.setManagerCode(userManager);
            request.setThisDay(format.format(new Date()));
            // 今天得普通订单数量  普通订单金额   批发订单数量  批发订单金额
            List<OrderDailyCountVo> resultList  =  orderDailyPayCountService.selectTodayPaidOrderCount(request);
            for (OrderDailyCountVo orderDailyCountVo : resultList) {
               if (orderDailyCountVo.getOrderType().equals(OrderType.NORMAL)){
                   map.put("todayPaidOrderNum", orderDailyCountVo.getOrderNum());
                   map.put("todayPaidOrderAmount", orderDailyCountVo.getOrderAmount());
               }
                if (orderDailyCountVo.getOrderType().equals(OrderType.WHOLESALE)){
                    map.put("todayPaidWholeSaleNum", orderDailyCountVo.getOrderNum());
                    map.put("todayPaidWholeSaleAmount", orderDailyCountVo.getOrderAmount());
                }
            }
        }, threadPoolExecutor);

        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            //近30天付款普通订单数  近30天付款普通订单金额  近30天付款批发订单数  近30天付款批发订单金额
            OrderDailyCountRequest request = new OrderDailyCountRequest();
            request.setManagerCode(userManager);
            request.setThisDay(format.format(new Date()));
            List<OrderDailyCountVo> resultList  = orderDailyPayCountService.selectThirtydayPaidOrderCount(request);
            for (OrderDailyCountVo orderDailyCountVo : resultList) {
                if (orderDailyCountVo.getOrderType().equals(OrderType.NORMAL)){
                    map.put("paidOrderNum30Day", orderDailyCountVo.getOrderNum());
                    map.put("paidOrderAmount30Day",orderDailyCountVo.getOrderAmount());
                }
                if (orderDailyCountVo.getOrderType().equals(OrderType.WHOLESALE)){
                    map.put("paidWholeSaleNum30Day", orderDailyCountVo.getOrderNum());
                    map.put("paidWholeSaleAmount30Day", orderDailyCountVo.getOrderAmount());
                }
            }
        }, threadPoolExecutor);

        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
            //本月销售额（美元）  本月订单数量
            OrderDailyCountRequest request = new OrderDailyCountRequest();
            request.setManagerCode(userManager);
            request.setStartDay(DateTools.getMonthStartDay(new Date()));
            request.setEndDay(DateTools.getMonthEndDay(new Date()));

            Long orderNum = 0L;
            BigDecimal currMonthSaleAmount = BigDecimal.ZERO;
            List<OrderDailyCountVo> resultList  =  orderDailyPayCountService.selectCurrMonthSaleAmount(request);
            for (OrderDailyCountVo orderDailyCountVo : resultList) {
                if (!orderDailyCountVo.getOrderType().equals(OrderType.STOCK)){
                    orderNum  = orderNum  + orderDailyCountVo.getOrderNum();
                    currMonthSaleAmount = currMonthSaleAmount.add(orderDailyCountVo.getOrderAmount());
                }
            }
            map.put("currMonthPaidUserNum", orderNum);
            map.put("currMonthSaleAmount", currMonthSaleAmount);
        }, threadPoolExecutor);


        try {
            CompletableFuture.allOf(future1, future2, future3).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, map);
    }

    /**
     * 仪表盘报表数据统计
     *
     * @param session
     * @return
     */
    @Override
    public BaseResponse dashboardOrderCharts(Session session) {
        Map<String, Object> map = new HashMap<>();
        String manager = null;
        //普通用户

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String userManager = manager;

        //报表数据
        double[] orderPaidNumArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] orderNumArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] orderAmountArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String startDay = DateTools.getLatest30DayStart(new Date());
        String endDay = DateTools.getLatest30DayEnd(new Date());
        List<String> dayStr = DateTools.getLatest30Days(new Date());
        Map<String, Integer> mapDate = DateTools.mapLatest30Days(new Date());



        List<DashboardOrderDto> normalDataList = orderDailyPayCountService.paidOrderDataByDay(startDay, endDay, userManager, OrderType.NORMAL);
        for (DashboardOrderDto dashboardOrderData : normalDataList) {
            Long orderNum = dashboardOrderData.getOrderPaidNum() == null ? 0 : dashboardOrderData.getOrderPaidNum();
            double orderAmount = dashboardOrderData.getOrderAmount() == null ? 0 : dashboardOrderData.getOrderAmount().doubleValue();
            String day = dashboardOrderData.getDayDate();
            orderPaidNumArr[mapDate.get(day) - 1] = orderNum;
            orderAmountArr[mapDate.get(day) - 1] = orderAmount;
            orderNumArr[mapDate.get(day) - 1] = orderNum;
        }
        List<DashboardOrderDto> wholesaleDataList = orderDailyPayCountService.paidOrderDataByDay(startDay, endDay, userManager,OrderType.WHOLESALE);
        for (DashboardOrderDto dashboardOrderData : wholesaleDataList) {
            Long orderNum = dashboardOrderData.getOrderPaidNum() == null ? 0 : dashboardOrderData.getOrderPaidNum();
            double orderAmount = dashboardOrderData.getOrderAmount() == null ? 0 : dashboardOrderData.getOrderAmount().doubleValue();
            String day = dashboardOrderData.getDayDate();
            orderPaidNumArr[mapDate.get(day) - 1] = orderPaidNumArr[mapDate.get(day) - 1] + orderNum;
            orderAmountArr[mapDate.get(day) - 1] = orderAmountArr[mapDate.get(day) - 1] + orderAmount;
            orderNumArr[mapDate.get(day) - 1] = orderNumArr[mapDate.get(day) - 1] + orderNum;
        }
        map.put("today", format.format(new Date()));
        map.put("orderPaidNumList", orderPaidNumArr);
        map.put("orderNumList", orderNumArr);
        map.put("orderAmountList", orderAmountArr);
        map.put("dayList", dayStr);
        return new BaseResponse(ResultCode.SUCCESS_CODE, map);
    }
/*    @Override
    public BaseResponse dashboardOrderCharts(Session session) {
        Map<String, Object> map = new HashMap<>();
        String manager = null;
        //普通用户
        if (session.getUserType() != BaseCode.USER_ROLE_SUPERADMIN) {
            ManagerInfoVo managerInfo = UserUtil.getManager(redisTemplate);
            manager = managerInfo == null ? null : managerInfo.getManagerCode();
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String userManager = manager;

        //报表数据
        double[] orderPaidNumArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] orderNumArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        double[] orderAmountArr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String startDay = DateTools.getLatest30DayStart(new Date());
        String endDay = DateTools.getLatest30DayEnd(new Date());
        List<String> dayStr = DateTools.getLatest30Days(new Date());
        Map<String, Integer> mapDate = DateTools.mapLatest30Days(new Date());

        //客户付款订单数、付款金额
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            //普通订单
            List<DashboardOrderDto> normalDataList = orderDao.paidNormalOrderDataByDay(startDay, endDay, userManager);
            for (DashboardOrderDto dashboardOrderData : normalDataList) {
                Long orderNum = dashboardOrderData.getOrderPaidNum() == null ? 0 : dashboardOrderData.getOrderPaidNum();
                double orderAmount = dashboardOrderData.getOrderAmount() == null ? 0 : dashboardOrderData.getOrderAmount().doubleValue();
                String day = dashboardOrderData.getDayDate();
                orderPaidNumArr[mapDate.get(day) - 1] = orderNum;
                orderAmountArr[mapDate.get(day) - 1] = orderAmount;
            }
            //批发订单
            List<DashboardOrderDto> wholesaleDataList = wholesaleOrderDao.paidWholesaleOrderDataByDay(startDay, endDay, userManager);
            for (DashboardOrderDto dashboardOrderData : wholesaleDataList) {
                Long orderNum = dashboardOrderData.getOrderPaidNum() == null ? 0 : dashboardOrderData.getOrderPaidNum();
                double orderAmount = dashboardOrderData.getOrderAmount() == null ? 0 : dashboardOrderData.getOrderAmount().doubleValue();
                String day = dashboardOrderData.getDayDate();
                orderPaidNumArr[mapDate.get(day) - 1] = orderPaidNumArr[mapDate.get(day) - 1] + orderNum;
                orderAmountArr[mapDate.get(day) - 1] = orderAmountArr[mapDate.get(day) - 1] + orderAmount;
            }
        }, threadPoolExecutor);

        //客户订单数
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            //普通订单
            List<DashboardOrderDto> normalOrderList = orderDao.normalOrderNumByDay(startDay, endDay, userManager);
            for (DashboardOrderDto data : normalOrderList) {
                String day = data.getDayDate();
                Long orderNum = data.getOrderNum() == null ? 0 : data.getOrderNum();
                orderNumArr[mapDate.get(day) - 1] = orderNum;
            }
            //批发订单
            List<DashboardOrderDto> wholesaleOrderList = wholesaleOrderDao.wholesaleOrderNumByDay(startDay, endDay, userManager);
            for (DashboardOrderDto data : wholesaleOrderList) {
                String day = data.getDayDate();
                Long orderNum = data.getOrderNum() == null ? 0 : data.getOrderNum();
                orderNumArr[mapDate.get(day) - 1] = orderNumArr[mapDate.get(day) - 1] + orderNum;
            }
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(future1, future2).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        map.put("today", format.format(new Date()));
        map.put("orderPaidNumList", orderPaidNumArr);
        map.put("orderNumList", orderNumArr);
        map.put("orderAmountList", orderAmountArr);
        map.put("dayList", dayStr);
        return new BaseResponse(ResultCode.SUCCESS_CODE, map);
    }*/

    /**
     * 未发货订单分析
     */
    @Override
    public BaseResponse waitTrackOrderData(Session session, WaitTrackOrderDataRequest request) {
        if (request.getDayNum() != 3 && request.getDayNum() != 5
                && request.getDayNum() != 7 && request.getDayNum() != 15) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        Integer dayNum = request.getDayNum();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = format.format(new Date());
        List<WaitTrackOrderData> orderList = new ArrayList<>();
        CompletableFuture future1 = CompletableFuture.runAsync(() -> {
            //普通订单未发货分析
            List<WaitTrackOrderData> list = orderDao.waitTrackOrderData(currDate, dayNum);
            orderList.addAll(list);
        }, threadPoolExecutor);
        List<WaitTrackOrderData> wholesaleList = new ArrayList<>();
        CompletableFuture future2 = CompletableFuture.runAsync(() -> {
            //批发订单未发货分析
            List<WaitTrackOrderData> list = wholesaleOrderDao.waitTrackOrderData(currDate, dayNum);
            wholesaleList.addAll(list);
        }, threadPoolExecutor);
        Map<String, Integer> map = new HashMap<>();
        try {
            CompletableFuture.allOf(future1, future2).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (WaitTrackOrderData waitTrackOrderData : wholesaleList) {
            map.put(waitTrackOrderData.getManagerCode(), waitTrackOrderData.getWholesaleNum());
        }
        for (WaitTrackOrderData waitTrackOrderData : orderList) {
            waitTrackOrderData.setWholesaleNum(map.get(waitTrackOrderData.getManagerCode()));
            map.remove(waitTrackOrderData.getManagerCode());
        }
        map.forEach((k,v) ->{
            WaitTrackOrderData waitTrackOrderData = new WaitTrackOrderData();
            waitTrackOrderData.setManagerCode(k);
            waitTrackOrderData.setWholesaleNum(v);
        });
        return new BaseResponse(ResultCode.SUCCESS_CODE, orderList);
    }

    /**
     * 未发货订单数据详情（客户维度）
     */
    @Override
    public BaseResponse waitTrackOrderDataDetails(Session session, WaitTrackOrderDataDetailsRequest request) {
        if (request.getDayNum() != 3 && request.getDayNum() != 5
                && request.getDayNum() != 7 && request.getDayNum() != 15) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        //1.普通订单 2.批发订单
        if (request.getType() != 1 && request.getType() != 2) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        Integer dayNum = request.getDayNum();
        String adminUserId = request.getAdminUserId();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = format.format(new Date());
        List<WaitTrackOrderDataDetails> list = new ArrayList<>();
        Set<Long> setIds = new HashSet<>();
        if (request.getType() == 1) {
            list = orderDao.waitTrackOrderDataDetails(currDate, adminUserId, dayNum);
            list.forEach(waitTrackOrderDataDetails -> {
                if (waitTrackOrderDataDetails.getCustomerId() != null) {
                    setIds.add(waitTrackOrderDataDetails.getCustomerId());
                }
            });
        }
        if (request.getType() == 2) {
            list = wholesaleOrderDao.waitTrackOrderDataDetails(currDate, adminUserId, dayNum);
            list.forEach(waitTrackOrderDataDetails -> {
                if (waitTrackOrderDataDetails.getCustomerId() != null) {
                    setIds.add(waitTrackOrderDataDetails.getCustomerId());
                }
            });
        }
        List<Long> customerIds = new ArrayList<>(setIds);
        if (customerIds.size() > 0) {
            CustomerByIdsRequest re = new CustomerByIdsRequest();
            re.setCustomerIds(customerIds);
            Map<Long, CustomerVo> customerVoMap = new HashMap<>();
            BaseResponse response = umsFeignClient.customerByIds(re);
            List<LinkedHashMap> customerList = (List<LinkedHashMap>) response.getData();
            customerList.forEach(v -> {
                CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(v), CustomerVo.class);
                customerVoMap.put(customerVo.getCustomerId(), customerVo);
            });
            list.forEach(waitTrackOrderDataDetail -> {
                CustomerVo c = customerVoMap.get(waitTrackOrderDataDetail.getCustomerId());
                waitTrackOrderDataDetail.setLoginName(c.getLoginName());
                waitTrackOrderDataDetail.setUserName(c.getUsername());
            });
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, list);
    }

    /**
     * 客户分析  排名  listAppUserSort
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse listAppUserSort(AppUserSortRequest request) throws CustomerException {
        if (request == null || request.getT() == null) {
            throw new CustomerException(CustomerExceptionEnum.LACK_OF_PARAMETER);
        }
        AppUserSortVo appUserSortVo = request.getT();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startDay = null;
        String endDay = null;
        if (appUserSortVo.getSortType() != null && appUserSortVo.getSortType() == 0) {
            //0 当日排名
            startDay = sdf.format(new Date());
            endDay = sdf.format(new Date());
        }
        if (appUserSortVo.getSortType() != null && appUserSortVo.getSortType() == 1) {
            //近一周排名
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            endDay = sdf.format(cal.getTime());
            cal.add(Calendar.DATE, -7);
            startDay = sdf.format(cal.getTime());
        }
        if (appUserSortVo.getSortType() != null && appUserSortVo.getSortType() == 2) {
            //近30天排名
            startDay = DateTools.getLatest30DayStart(new Date());
            endDay = DateTools.getLatest30DayEnd(new Date());
        }
        if (appUserSortVo.getSortType() != null && appUserSortVo.getSortType() == 3) {
            //总排名
            startDay = null;
            endDay = null;
        }
        request.setEndDay(endDay);
        request.setStartDay(startDay);
        List<AppUserSortVo> list = storeOrderService.listAppUserSortPage(request);
        for (AppUserSortVo userSortVo : list) {
            userSortVo.setSortType(appUserSortVo.getSortType());
            // 根据 customerId  查  用户信息
            BaseResponse baseResponse = umsFeignClient.customerInfo(userSortVo.getCustomerId());
            if (baseResponse.getCode() != ResultCode.SUCCESS_CODE) {
                break;
            }
            CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(baseResponse.getData()), CustomerVo.class);
            userSortVo.setCustomerName(customerVo.getUsername());
            userSortVo.setEmail(customerVo.getEmail());
            userSortVo.setRegisterDate(customerVo.getCreateTime());
            // 从redis中获取manager信息
            String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, customerVo.getCustomerId().toString());
            userSortVo.setManagerCode(managerCode);
//            ManagerInfoVo managerInfoVo = (ManagerInfoVo) redisTemplate.opsForValue().get(RedisKey.STRING_MANAGER_INFO + managerCode);
//            userSortVo.setUserManager(managerInfoVo.getManagerName());
        }
        Long count = storeOrderService.listAppUserSortCount(request);
        request.setTotal(count);
        return BaseResponse.success(list, request);
    }

    @Override
    public BaseResponse distributionOrderPie(DistributionOrderQuery query) {

        Date queryDate = query.getQueryDate();
        Long orderSourceId = query.getOrderSourceId();

        HashMap<String, String> map = new HashMap<>();
        //获取当前月的第一天
        String startDay = DateTools.getMonthStartDay(queryDate);
        //获取当前月的最后一天
        String endDay = DateTools.getMonthEndDay(queryDate);

        //渠道当月正常订单数量
        int normalOrder = packageInfoService.countNormalOrderBySource(startDay, endDay, orderSourceId);
        //渠道当月补发订单数量
        int againOrder = packageInfoService.countAgainOrderBySource(startDay, endDay, orderSourceId);
        //渠道当月批发订单数量
        int wholeSaleOrder = packageInfoService.countWholeSaleOrderBySource(startDay, endDay, orderSourceId);
        List<EchartsPie> list = new ArrayList<>();
        EchartsPie echartsPie2 = new EchartsPie("正常订单", normalOrder);
        EchartsPie echartsPie3 = new EchartsPie("补发订单", againOrder);
        EchartsPie echartsPie4 = new EchartsPie("批发订单", wholeSaleOrder);
        list.add(echartsPie2);
        list.add(echartsPie3);
        list.add(echartsPie4);
        String pieJson = JSON.toJSONString(list);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        map.put("today", sdf.format(queryDate));
        map.put("pieJson", pieJson);
        return BaseResponse.success(map);
    }

    @Override
    public BaseResponse saleTotal(DistributionOrderQuery query) {
        String startDay= DateTools.getMonthStartDay(query.getQueryDate());
        //获取当前月的最后一天
        String endDay=DateTools.getMonthEndDay(query.getQueryDate());
        //美元汇率
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        PackageUsdRate packageUsdRate = packageUsdRateService.queryPackageUsdRate(format.format(query.getQueryDate()));
        BigDecimal usdRate = new BigDecimal(PackageInfoService.USD_RATE);
        if (packageUsdRate != null && packageUsdRate.getUsdRate() != null) {
            usdRate = packageUsdRate.getUsdRate();
        }
        usdRate = usdRate.setScale(2, BigDecimal.ROUND_HALF_UP);
        return BaseResponse.success(sale(startDay,endDay,query.getOrderSourceId(),usdRate));
    }


    public SaleTotalVo sale(String startDay, String endDay, Long orderSourceId, BigDecimal usdRate) {
        SaleTotalVo saleTotal = new SaleTotalVo();

        //1 渠道当月包裹订单总数
        int countPackageOrder = packageInfoService.countPackageOrderBySource(startDay, endDay, orderSourceId);

        //2 当月发货包裹批发订单销售额
        BigDecimal totalPackageWholeSaleOrderAmount = packageInfoService.totalNormalWholeSaleOrderAsalesBySource(startDay, endDay, orderSourceId);
        totalPackageWholeSaleOrderAmount = totalPackageWholeSaleOrderAmount == null ? BigDecimal.ZERO : totalPackageWholeSaleOrderAmount.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);

        //3 当月发货包裹订单销售额
        BigDecimal totalPackageOrderAsales = packageInfoService.totalNormalOrderAsalesBySource(startDay, endDay, orderSourceId);
        totalPackageOrderAsales = totalPackageOrderAsales == null ? BigDecimal.ZERO : totalPackageOrderAsales.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);

        //4 当月发货包裹总销售额
        totalPackageOrderAsales = totalPackageOrderAsales.add(totalPackageWholeSaleOrderAmount);

        //5 当月发货包裹订单采购成本
        BigDecimal totalPackagePurchaseCost = packageInfoService.totalPackagePurchaseCost(startDay, endDay, orderSourceId);
        totalPackagePurchaseCost = totalPackagePurchaseCost == null ? BigDecimal.ZERO : totalPackagePurchaseCost.setScale(2, BigDecimal.ROUND_HALF_UP);

        //6 当月发货包裹订单物流成本
        BigDecimal totalPackageLogisticsCost = packageInfoService.totalPackageLogisticsCost(startDay, endDay, orderSourceId);
        totalPackageLogisticsCost = totalPackageLogisticsCost == null ? BigDecimal.ZERO : totalPackageLogisticsCost.setScale(2, BigDecimal.ROUND_HALF_UP);

        //7 当月补发包裹采购成本
        BigDecimal againOrderPurchaseCost = packageInfoService.totalPackageAgainOrderPurchaseCost(startDay, endDay, orderSourceId);
        againOrderPurchaseCost = againOrderPurchaseCost == null ? BigDecimal.ZERO : againOrderPurchaseCost.setScale(2, BigDecimal.ROUND_HALF_UP);

        //8 当月补发包裹物流成本
        BigDecimal againOrderLogisticsCost = packageInfoService.totalPackageAgainOrderLogisticsCost(startDay, endDay, orderSourceId);
        againOrderLogisticsCost = againOrderLogisticsCost == null ? BigDecimal.ZERO : againOrderLogisticsCost.setScale(2, BigDecimal.ROUND_HALF_UP);

        //9 当月发货包裹订单返点
        BigDecimal totalPackageOrderBenefits = orderDailyPayCountService.totalPackageOrderBenefits(startDay, endDay, orderSourceId);
        totalPackageOrderBenefits = totalPackageOrderBenefits == null ? BigDecimal.ZERO : totalPackageOrderBenefits.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);

        //10 当月发货包裹批发订单返点
        BigDecimal totalPackageWholeSaleOrderBenefits = orderDailyPayCountService.totalPackageWholeSaleOrderBenefits(startDay, endDay, orderSourceId);
        totalPackageWholeSaleOrderBenefits = totalPackageWholeSaleOrderBenefits == null ? BigDecimal.ZERO : totalPackageWholeSaleOrderBenefits.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        totalPackageOrderBenefits = totalPackageOrderBenefits.add(totalPackageWholeSaleOrderBenefits);

        //11 当月确认已发货订单退款金额 按照赛盒渠道统计
        BigDecimal monthRefundTrackingYesAmountNew = packageInfoService.monthRefundTrackingYesAmountByOrderSourceId(startDay, endDay, orderSourceId);
        monthRefundTrackingYesAmountNew = monthRefundTrackingYesAmountNew == null ? BigDecimal.ZERO : monthRefundTrackingYesAmountNew.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);

        //12 当月确认已发货批发订单退款金额 按照赛盒渠道统计
        BigDecimal monthWholeSaleRefundTrackingYesAmountNew = packageInfoService.monthWholeSaleRefundTrackingYesAmountByOrderSourceId(startDay, endDay, orderSourceId);
        monthWholeSaleRefundTrackingYesAmountNew = monthWholeSaleRefundTrackingYesAmountNew == null ? BigDecimal.ZERO : monthWholeSaleRefundTrackingYesAmountNew.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);

        //13 新批发已确认已发货退款
        saleTotal.setWholesaleOrderRefundTrackingYesAmountNew(monthWholeSaleRefundTrackingYesAmountNew);

        //14 新的退款统计
        monthRefundTrackingYesAmountNew = monthRefundTrackingYesAmountNew.add(monthWholeSaleRefundTrackingYesAmountNew);

        //15 当月确认未发货订单退款金额
        BigDecimal monthRefundTrackingNoAmount = packageInfoService.monthRefundTrackingNoAmount(startDay, endDay, orderSourceId);
        monthRefundTrackingNoAmount = monthRefundTrackingNoAmount == null ? BigDecimal.ZERO : monthRefundTrackingNoAmount.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);

        //16 当月确认未发货批发订单退款金额
        BigDecimal monthWholeSaleRefundTrackingNoAmount = packageInfoService.monthWholeSaleRefundTrackingNoAmount(startDay, endDay, orderSourceId);
        monthWholeSaleRefundTrackingNoAmount = monthWholeSaleRefundTrackingNoAmount == null ? BigDecimal.ZERO : monthWholeSaleRefundTrackingNoAmount.
                multiply(usdRate).setScale(2, BigDecimal.ROUND_HALF_UP);
        monthRefundTrackingNoAmount = monthRefundTrackingNoAmount.add(monthWholeSaleRefundTrackingNoAmount);
        saleTotal.setCountPackageOrder(countPackageOrder);
        saleTotal.setTotalPackageOrderAsales(totalPackageOrderAsales);
        saleTotal.setTotalPackagePurchaseCost(totalPackagePurchaseCost);
        saleTotal.setTotalPackageLogisticsCost(totalPackageLogisticsCost);
        saleTotal.setAgainOrderPurchaseCost(againOrderPurchaseCost);
        saleTotal.setAgainOrderLogisticsCost(againOrderLogisticsCost);
        saleTotal.setTotalPackageOrderBenefits(totalPackageOrderBenefits);
        //新的已确认已发货退款统计
        saleTotal.setMonthRefundTrackingYesAmountNew(monthRefundTrackingYesAmountNew);
        saleTotal.setMonthRefundTrackingNoAmount(monthRefundTrackingNoAmount);
        saleTotal.setUsdRate(usdRate);

        //当月利润=当月发货包裹订单销售额-当月发货包裹订单采购成本-当月发货包裹订单物流成本-当月确认已发货订单退款金额-当月发货包裹订单返点
        BigDecimal totalPackageProfit = totalPackageOrderAsales.subtract(totalPackagePurchaseCost)
                .subtract(totalPackageLogisticsCost)
                .subtract(monthRefundTrackingYesAmountNew)
                .subtract(totalPackageOrderBenefits);
        saleTotal.setTotalPackageProfit(totalPackageProfit);
        return saleTotal;
    }

    /**
     * 销售统计 echarts
     *
     * @param query
     * @return
     */
    @Override
    public HashMap<String, String> listSaleData(DistributionOrderQuery query) {
        Date queryDate = query.getQueryDate();
        Long orderSourceId = query.getOrderSourceId();
        //获取当前月的第一天
        String startDay = DateTools.getMonthStartDay(queryDate);
        //获取当前月的最后一天
        String endDay = DateTools.getMonthEndDay(queryDate);
        List<String> dayStr = DateTools.getMonthDays(queryDate);
        Map<String, Integer> mapDate = DateTools.mapMonthDays(queryDate);

        //美元汇率
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        PackageUsdRate packageUsdRate = packageUsdRateService.queryPackageUsdRate(format.format(queryDate));
        BigDecimal usdRate = new BigDecimal(PackageInfoService.USD_RATE);
        if (packageUsdRate != null && packageUsdRate.getUsdRate() != null) {
            usdRate = packageUsdRate.getUsdRate();
        }
        usdRate = usdRate.setScale(2, BigDecimal.ROUND_HALF_UP);

        List<PackageData> dataList = new ArrayList<>();
        List<PackageData> orderDataList = new ArrayList<>();
        List<PackageData> wholeSaleDataList = new ArrayList<>();
        if (queryDate.compareTo(new Date()) <= 0) {
            OrderBenefitsRequest orderBenefitsRequest = new OrderBenefitsRequest(startDay, endDay, orderSourceId, usdRate.doubleValue());
            //日期在当前日期以内  获取包裹支出费用
            dataList = packageInfoService.packageMonthAmount(startDay, endDay, orderSourceId);
            // 包裹普通订单收入
            orderDataList = packageInfoService.packageMonthOrderAmount(startDay, endDay, orderSourceId, usdRate.doubleValue());
            BaseResponse orderBenefitsListResponse = umsFeignClient.packageMonthOrderBenefitsListAmount(orderBenefitsRequest);
            if (orderBenefitsListResponse.getCode() == ResultCode.SUCCESS_CODE) {
                List<OrderBenefitsVo> orderBenefitsList = JSON.parseArray(JSON.toJSONString(orderBenefitsListResponse.getData()), OrderBenefitsVo.class);
                for (PackageData packageData : orderDataList) {
                    for (OrderBenefitsVo orderBenefitsVo : orderBenefitsList) {
                        if (StringUtils.equals(packageData.getDayDate(), orderBenefitsVo.getDayDate())) {
                            packageData.setOrderAmount(
                                    packageData.getPackageAmount().subtract(orderBenefitsVo.getBenefitsAmount())
                            );
                            break;
                        }
                    }
                }
            }
            // 包裹批发订单收入
            wholeSaleDataList = packageInfoService.packageMonthWholeSaleOrderAmount(startDay, endDay, orderSourceId, usdRate.doubleValue());
            BaseResponse wholeSaleOrderBenefitsListRespone = umsFeignClient.packageMonthWholeSaleOrderBenefitsListAmount(orderBenefitsRequest);
            if (wholeSaleOrderBenefitsListRespone.getCode() == ResultCode.SUCCESS_CODE) {
                List<OrderBenefitsVo> wholeSaleBenefitsList = JSON.parseArray(JSON.toJSONString(wholeSaleOrderBenefitsListRespone.getData()), OrderBenefitsVo.class);
                for (PackageData packageData : wholeSaleDataList) {
                    for (OrderBenefitsVo orderBenefitsVo : wholeSaleBenefitsList) {
                        if (StringUtils.equals(packageData.getDayDate(), orderBenefitsVo.getDayDate())) {
                            packageData.setOrderAmount(
                                    packageData.getPackageAmount().subtract(orderBenefitsVo.getBenefitsAmount())
                            );
                            break;
                        }
                    }
                }
            }
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(queryDate);
        //获取当前月的总天数
        int dayNumOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        ;

        double[] packAmountArr = new double[dayNumOfMonth];
        double[] packOrderArr = new double[dayNumOfMonth];
        double[] packProfitArr = new double[dayNumOfMonth];

        for (PackageData data : dataList) {
            String day = data.getDayDate();
            if (StringUtils.isBlank(day)) {
                continue;
            }
            double packAmount = data.getPackageAmount() == null ? 0.0 : data.getPackageAmount().doubleValue();
            packAmountArr[mapDate.get(day) - 1] = packAmount;
        }
        for (PackageData d : orderDataList) {
            String day = d.getDayDate();
            if (StringUtils.isBlank(day)) {
                continue;
            }
            double packOrderAmount = d.getOrderAmount() == null ? 0.0 : d.getOrderAmount().doubleValue();
            packOrderArr[mapDate.get(day) - 1] = packOrderAmount;
        }
        //加上批发订单金额
        for (PackageData p : wholeSaleDataList) {
            String day = p.getDayDate();
            if (StringUtils.isBlank(day)) {
                continue;
            }
            double amount = p.getOrderAmount() == null ? 0.0 : p.getOrderAmount().doubleValue();
            double oldAmount = packOrderArr[mapDate.get(day) - 1];
            packOrderArr[mapDate.get(day) - 1] = oldAmount + amount;
        }
        for (int i = 0; i < dayNumOfMonth; i++) {
            //收入-支出
            packProfitArr[i] = new BigDecimal(packOrderArr[i] - packAmountArr[i]).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        }


        HashMap<String, String> map = new HashMap<>();
        //包裹支出
        String packAmountArrJson = JSON.toJSONString(packAmountArr);
        //包裹收入
        String packOrderArrJson = JSON.toJSONString(packOrderArr);
        //利润
        String packProfitArrJson = JSON.toJSONString(packProfitArr);

        String dayStrJson = JSON.toJSONString(dayStr);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        map.put("today", sdf.format(queryDate));
        map.put("packAmountArrJson", packAmountArrJson);
        map.put("packOrderArrJson", packOrderArrJson);
        map.put("packProfitArrJson", packProfitArrJson);
        map.put("dayStrJson", dayStrJson);
        return map;
    }

    /**
     * 销售统计echarts图 右上导出当月订单发货包裹批发订单销售额
     *
     * @param query
     * @return
     */
    @Override
    public List<OrderSaleVo> exportNormalOrderSale(DistributionOrderQuery query) {
        String startDay = DateTools.getMonthStartDay(query.getQueryDate());
        //获取当前月的最后一天
        String endDay = DateTools.getMonthEndDay(query.getQueryDate());

        List<OrderSaleVo> dataVoList = packageInfoService.listNormalOrderAsalesBySource(startDay, endDay, query.getOrderSourceId());
        return dataVoList;
    }

    /**
     * 销售统计echarts图 右上导出当月批发订单发货包裹批发订单销售额
     *
     * @param query
     * @return
     */
    @Override
    public List<OrderSaleVo> exportWholesaleOrderSale(DistributionOrderQuery query) {
        String startDay = DateTools.getMonthStartDay(query.getQueryDate());
        //获取当前月的最后一天
        String endDay = DateTools.getMonthEndDay(query.getQueryDate());

        List<OrderSaleVo> dataVoList = packageInfoService.exportWholesaleOrderSale(startDay, endDay, query.getOrderSourceId());
        return dataVoList;
    }

    /**
     * 按时间区间导出
     * @param query
     * @return
     */
    @Override
    public BaseResponse exportSaleTotalByDate(ExcelDostributionOrderQuery query) throws CustomerException, ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date before = format.parse(query.getStartDay());
        Date after = format.parse(query.getEndDay());
        int dayNum = DateTools.getDistanceOfTwoDay(before, after);
        if(dayNum>45){
           throw new CustomerException(CustomerExceptionEnum.TIME_INTERVAL_SHOULD_BE_NO_MORE);
        }
        //美元汇率
        BigDecimal usdRate = BigDecimal.ONE;
        List<ApiOrderSource> apiOrderSources =new ArrayList<>();
        if (query.getOrderSourceId() == -1){
             apiOrderSources = managerOrderSourceList();
        }else{
            ApiOrderSource  apiOrderSource = new ApiOrderSource();
            apiOrderSource.setID(Integer.valueOf(query.getOrderSourceId().toString()));
            apiOrderSource.setOrderSourceName(query.getOrderSourceName());
            apiOrderSources.add(apiOrderSource);
        }
      /*  for (ApiOrderSource apiOrderSource : apiOrderSources) {
            SaleTotalVo sale = sale(query.getStartDay(), query.getEndDay(), Long.parseLong(apiOrderSource.getID().toString()),usdRate);
            sale.setOrderSourceName(apiOrderSource.getOrderSourceName());
            resultList.add(sale);
        }*/
        List<SaleTotalVo> resultList = Collections.synchronizedList(new ArrayList<SaleTotalVo>());
        CountDownLatch latch = new CountDownLatch(apiOrderSources.size());
     //   ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future<String>> futureTaskList  = new ArrayList<Future<String>>(5);
        final List<ApiOrderSource> apiOrderSourcess = apiOrderSources;
        final BigDecimal usdRates =  usdRate;
        for(int i = 0; i < apiOrderSourcess.size(); i++){
            int finalI = i;
            futureTaskList.add(threadPoolExecutor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {

                    try {
                        SaleTotalVo sale = sale(query.getStartDay(), query.getEndDay(),  Long.parseLong(apiOrderSourcess.get(finalI).getID().toString()),usdRate);
                        sale.setOrderSourceName(apiOrderSourcess.get(finalI).getOrderSourceName());
                        resultList.add(sale);
                        return "ok...";
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return null;
                    }finally {
                        latch.countDown();
                    }
                }
            }));
        }
        try {
            latch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        try {
            for (Future<String> future : futureTaskList) {
                System.out.println(future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return BaseResponse.success(resultList);
    }

    /**
     * 按月份导出
     * @param query
     * @return
     */
    @Override
    public BaseResponse exportSaleTotal(DistributionOrderQuery query) {

        String startDay= DateTools.getMonthStartDay(query.getQueryDate());
        //获取当前月的最后一天
        String endDay=DateTools.getMonthEndDay(query.getQueryDate());
        //美元汇率
        //美元汇率
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        PackageUsdRate packageUsdRate = packageUsdRateService.queryPackageUsdRate(format.format(query.getQueryDate()));
        BigDecimal usdRate = new BigDecimal(PackageInfoService.USD_RATE);
        if (packageUsdRate != null && packageUsdRate.getUsdRate() != null) {
            usdRate = packageUsdRate.getUsdRate();
        }
        usdRate = usdRate.setScale(2, BigDecimal.ROUND_HALF_UP);
        List<ApiOrderSource> apiOrderSources =new ArrayList<>();
        if (query.getOrderSourceId() == -1){
            apiOrderSources = managerOrderSourceList();
        }else{
            ApiOrderSource  apiOrderSource = new ApiOrderSource();
            apiOrderSource.setID(Integer.valueOf(query.getOrderSourceId().toString()));
            apiOrderSource.setOrderSourceName(query.getOrderSourceName());
            apiOrderSources.add(apiOrderSource);
        }

        List<SaleTotalVo> resultList = Collections.synchronizedList(new ArrayList<SaleTotalVo>());
        CountDownLatch latch = new CountDownLatch(apiOrderSources.size());
     //   ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future<SaleTotalVo>> futureTaskList  = new ArrayList<Future<SaleTotalVo>>(5);
        final List<ApiOrderSource> apiOrderSourcess = apiOrderSources;
        final BigDecimal usdRates =  usdRate;
        for(int i = 0; i < apiOrderSourcess.size(); i++){
            int finalI = i;
            futureTaskList.add(threadPoolExecutor.submit(new Callable<SaleTotalVo>() {
                @Override
                public SaleTotalVo call() throws Exception {

                    try {
                        SaleTotalVo sale = sale(startDay, endDay, Long.parseLong(apiOrderSourcess.get(finalI).getID().toString()), usdRates);
                        sale.setOrderSourceName(apiOrderSourcess.get(finalI).getOrderSourceName());
                        resultList.add(sale);
                        return sale;
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return null;
                    }finally {
                        latch.countDown();
                    }
                }
            }));
        }
        try {
            latch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        /*try {
            for (Future<SaleTotalVo> future : futureTaskList) {
                System.err.println(future.get().toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        return BaseResponse.success(resultList);
    }

    /**
     * 获取客户经理赛盒渠道
     */
    public List<ApiOrderSource> managerOrderSourceList(){
        List<ApiOrderSource> orderSourceList=new ArrayList<>();
        ApiGetOrderSourceResponse apiGetOrderSourceResponse=
                SaiheSourceApi.getOrderSourceList(SaiheConfig.UPEDGE_ORDER_SOURCE_TYPE);
        if(apiGetOrderSourceResponse!=null&&
                apiGetOrderSourceResponse.getGetOrderSourceListResult()!=null&&
                apiGetOrderSourceResponse.getGetOrderSourceListResult().getOrderSourceList()!=null){
            orderSourceList=apiGetOrderSourceResponse.getGetOrderSourceListResult()
                    .getOrderSourceList().getApiOrderSource();
        }
        ApiOrderSource apiOrderSource = new ApiOrderSource();
        apiOrderSource.setID(-1);
        apiOrderSource.setOrderSourceName("ALL");
        orderSourceList.add(apiOrderSource);
        return orderSourceList;
    }


}
