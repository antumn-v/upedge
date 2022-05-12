package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.request.ManagerActualRequest;
import com.upedge.common.model.order.vo.AllOrderAmountVo;
import com.upedge.common.model.order.vo.ManagerActualVo;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.enums.OrderTagEnum;
import com.upedge.oms.modules.order.dto.AppOrderListDto;
import com.upedge.oms.modules.order.dto.OrderAnalysisDto;
import com.upedge.oms.modules.order.dto.PandaOrderListDto;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderAddress;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderListResponse;
import com.upedge.oms.modules.order.response.OrderUpdateResponse;
import com.upedge.oms.modules.order.service.OrderActionService;
import com.upedge.oms.modules.order.service.OrderProfitService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.*;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.orderShippingUnit.vo.OrderShippingUnitVo;
import com.upedge.oms.modules.tickets.service.SupportTicketsService;
import com.upedge.oms.modules.tickets.vo.SupportTicketsVo;
import com.upedge.oms.scheduler.PackageScheduler;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author author
 */
@Api(tags = "订单管理")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    OrderProfitService orderProfitService;

    @Autowired
    OrderActionService orderActionService;

    @Autowired
    SupportTicketsService supportTicketsService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private OrderShippingUnitService orderShippingUnitService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    PackageScheduler packageScheduler;

    @ApiOperation("订单搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tags", value = "默认值为TO_ORDER,可取值：ALL,TO_ORDER,IN_PROCESSING,SHIPPED,CANCELED,REFUNDS,RESHIPPED", required = true),
            @ApiImplicitParam(name = "platOrderName", value = "店铺订单号", required = false),
            @ApiImplicitParam(name = "platOrderNames", value = "店铺订单号集合，批量搜索", paramType = "List"),
            @ApiImplicitParam(name = "orderId", value = "", required = false),
            @ApiImplicitParam(name = "paymentId", value = "交易ID"),
            @ApiImplicitParam(name = "financialStatus", value = "0=Paid 1=Partially Refunded 2=Refunded"),
            @ApiImplicitParam(name = "fulfillmentStatus", value = "0=Unfulfilled 1=Partial 2=Fulfilled"),
            @ApiImplicitParam(name = "orgId", value = "组织Id"),
            @ApiImplicitParam(name = "toAreaId", value = "地区ID"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "shipNumber", value = "物流单号"),
            @ApiImplicitParam(name = "customer", value = "收件人"),
            @ApiImplicitParam(name = "productTitle", value = "店铺产品标题"),
            @ApiImplicitParam(name = "sku", value = "店铺变体SKU")
    })
    @PostMapping("/list")
    public BaseResponse appOrderList(@RequestBody AppOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        AppOrderListDto appOrderListDto = request.getT();
        if (null == appOrderListDto) {
            appOrderListDto = new AppOrderListDto();
        } else {
            appOrderListDto.initOrderState();
//            appOrderListDto.initDateRange();
        }
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            List<Long> orgIds = session.getOrgIds();
            if (ListUtils.isEmpty(orgIds)) {
                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, new ArrayList<>(), request);
            }
            appOrderListDto.setOrgIds(orgIds);
        }
        appOrderListDto.setCustomerId(session.getCustomerId());

        if (ListUtils.isNotEmpty(appOrderListDto.getPlatOrderNames())) {
            request.setPageSize(null);
        } else {
            request.initFromNum();
        }

        if (appOrderListDto.getTags().equals("PAID")) {
            appOrderListDto.setPayState(null);
            request.setCondition("o.pay_state > 0");
        }
        if (appOrderListDto.getTags().equals("REFUNDS")) {
            appOrderListDto.setRefundState(null);
            request.setCondition("o.refund_state > 0");
        }

        request.setT(appOrderListDto);
        List<AppOrderVo> appOrderVos = orderService.selectAppOrderList(request);
        return new OrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, appOrderVos, request);
    }


/*    @ApiOperation("订单数量,参数同订单列表")
    @PostMapping("/app/count")
    public BaseResponse appOrderCount(@RequestBody AppOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        AppOrderListDto appOrderListDto = request.getT();
        Map<String, Long> map = new HashMap<>();

        for (OrderTagEnum tag : OrderTagEnum.values()) {
            if (null == appOrderListDto) {
                appOrderListDto = new AppOrderListDto();
            } else {
                appOrderListDto.setTags(tag.name());
                appOrderListDto.initOrderState();
                appOrderListDto.initDateRange();
            }

            if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
                List<Long> orgIds = session.getOrgIds();
                if (ListUtils.isEmpty(orgIds)) {
                    return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, new ArrayList<>(), request);
                }
                appOrderListDto.setOrgIds(orgIds);
            }

            appOrderListDto.setCustomerId(session.getCustomerId());
            if (tag.name().equals("IN_PROCESSING")) {
                appOrderListDto.setPayState(null);
                request.setCondition("o.pay_state > 0");
            } else if (tag.name().equals("REFUNDS")) {
                appOrderListDto.setRefundState(null);
                request.setCondition("o.refund_state > 0");
            } else {
                appOrderListDto.setPayState(tag.getPayState());
                appOrderListDto.setRefundState(tag.getRefundState());
                appOrderListDto.setShipState(tag.getShipState());
                request.setCondition(null);
            }
            request.setT(appOrderListDto);
            Long total = orderService.selectAppOrderCount(request);
            map.put(tag.name(), total);
        }

        return new OrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, map, request);
    }*/


    @ApiOperation("订单数量,参数同订单列表")
    @PostMapping("/count")
    public BaseResponse appOrderCount(@RequestBody AppOrderListRequest appOrderListRequest) {

        Session session = UserUtil.getSession(redisTemplate);

        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            List<Long> orgIds = session.getOrgIds();
            if (ListUtils.isEmpty(orgIds)) {
                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, new ArrayList<>(), appOrderListRequest);
            }
        }

        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap();
        CountDownLatch latch = new CountDownLatch(OrderTagEnum.values().length);
        for (OrderTagEnum tag : OrderTagEnum.values()) {
            threadPoolExecutor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    AppOrderListRequest request = new AppOrderListRequest();
                    BeanUtils.copyProperties(appOrderListRequest, request);
                    try {
                        AppOrderListDto appOrderList = request.getT();
                        AppOrderListDto appOrderListDto = new AppOrderListDto();
                        BeanUtils.copyProperties(appOrderList, appOrderListDto);
                        if (null == appOrderListDto) {
                            appOrderListDto = new AppOrderListDto();
                        } else {
                            appOrderListDto.setTags(tag.name());
                            appOrderListDto.initOrderState();
//                            appOrderListDto.initDateRange();
                        }

                        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
                            List<Long> orgIds = session.getOrgIds();
                            appOrderListDto.setOrgIds(orgIds);
                        }

                        appOrderListDto.setCustomerId(session.getCustomerId());
                        if (tag.name().equals("IN_PROCESSING")) {
                            appOrderListDto.setPayState(null);
                            request.setCondition("o.pay_state > 0");
                        } else if (tag.name().equals("REFUNDS")) {
                            appOrderListDto.setRefundState(null);
                            request.setCondition("o.refund_state > 0");
                        } else {
                            appOrderListDto.setPayState(tag.getPayState());
                            appOrderListDto.setRefundState(tag.getRefundState());
                            appOrderListDto.setShipState(tag.getShipState());
                            request.setCondition(null);
                        }
                        request.setT(appOrderListDto);
                        Long total = orderService.selectAppOrderCount(request);
                        map.put(tag.name(), total);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    } finally {
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        return new OrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, map, appOrderListRequest);
    }

    //    @ApiOperation("订单所有数量")
//    @PostMapping("/allCount")
    public BaseResponse appOrderAllCount(@RequestBody AppOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        AppOrderListDto appOrderListDto = new AppOrderListDto();
        if (session.getUserType() == BaseCode.USER_ROLE_NORMAL) {
            List<Long> orgIds = session.getOrgIds();
            if (ListUtils.isEmpty(orgIds)) {
                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, new ArrayList<>(), request);
            }
            appOrderListDto.setOrgIds(orgIds);
        }
        appOrderListDto.setShipState(0);
        appOrderListDto.setPayState(0);
        appOrderListDto.setRefundState(0);
        appOrderListDto.setBeginTime(null);
        appOrderListDto.setEndTime(null);
        appOrderListDto.setCustomerId(session.getCustomerId());
        request.setT(appOrderListDto);
        Long total = orderService.selectAppOrderCount(request);
        request.setTotal(total);
        return BaseResponse.success(total, request);
    }


    //    @ApiOperation("未支付订单统计")
//    @GetMapping("/upPaid/count")
    public BaseResponse customerOrderUnPaidCount() {
        Session session = UserUtil.getSession(redisTemplate);
        List<UnPaidOrderCountVo> unPaidOrderCountVos = orderService.countCustomerUnPaidOrder(session);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, unPaidOrderCountVos);
    }

    //    @ApiOperation("订单产品列表")
//    @GetMapping("/{orderId}/app/items")
    public BaseResponse getAppOrderItems(@PathVariable Long orderId) {
        List<AppOrderItemVo> appOrderItemVos = orderService.selectAppOrderItemByOrderId(orderId);
        if (ListUtils.isEmpty(appOrderItemVos)) {
            return BaseResponse.failed();
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, appOrderItemVos);
    }

    @ApiOperation("app订单详情")
    @GetMapping("/{id}/detail")
    public BaseResponse appOrderDetail(@PathVariable Long id) {
        AppOrderVo appOrderVo = orderService.appOrderDetail(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, appOrderVo);
    }

    @ApiOperation("订单地址")
    @GetMapping("/{id}/address")
    public BaseResponse orderAddress(@PathVariable Long id) {
        OrderAddress orderAddress = orderService.getOrderAddress(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, orderAddress);
    }

    /**
     * 订单匹配运输规则
     *
     * @param id
     * @return
     */
    @ApiOperation("订单匹配运输规则")
    @PostMapping("/{id}/shipRule/match")
    public BaseResponse orderMatchShipRule(@PathVariable Long id) {
        OrderShipRuleDetail detail = orderService.matchShipRule(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, detail);

    }

    //    @ApiOperation("订单利润")
//    @GetMapping("/{id}/profit")
    public BaseResponse orderProfit(@PathVariable Long id) {
        OrderProfitVo profit = orderProfitService.selectByPrimaryKey(id);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, profit);
    }

    @ApiOperation("订单ticket详情")
    @GetMapping("/ticketDetail/{orderId}")
    public BaseResponse orderTicketDetail(@PathVariable Long orderId){
        SupportTicketsVo supportTicketsVo = supportTicketsService.selectOpenTicketDetailByOrderId(orderId);
        if(null == supportTicketsVo){
            return new BaseResponse(-2,Constant.MESSAGE_FAIL);
        }
        return BaseResponse.success(supportTicketsVo);
    }

    /**
     * 订单运输方式列表
     *
     * @param id
     * @return
     */
    @ApiOperation("订单运输方式")
    @GetMapping("/{id}/ship/list")
    public BaseResponse orderShipList(@PathVariable Long id) {
//        Order order = orderService.selectByPrimaryKey(id);
//        if (order!= null){
//            List<ShipDetail> shipDetails = orderService.orderLocalWarehouseShipMethods(order.getId(),order.getToAreaId());
//            for (ShipDetail shipDetail : shipDetails) {
//                shipDetail.setPrice(shipDetail.getServiceFee().add(shipDetail.getPrice()));
//            }
//            return BaseResponse.success(shipDetails,id);
//        }
        List<OrderShipMethodVo> shipDetails = orderService.orderShipList(id);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, shipDetails, id);
    }

    @ApiOperation("订单修改运输方式")
    @PostMapping("/{id}/ship/update")
    public BaseResponse orderUpdateShipDetail(@PathVariable Long id, @RequestBody ShipDetail shipDetail) {
        String warehouseCode = RequestUtil.getWarehouseCode();
        shipDetail = orderService.updateShipDetail(id,shipDetail);
        if (null == shipDetail){
            return BaseResponse.failed();
        }
        return BaseResponse.success(shipDetail);
    }

    //    @ApiOperation("订单能否发货判断")
//    @PostMapping("/ship/verify")
    public BaseResponse orderShipVerify(@RequestBody List<Long> ids) {
        String warehouseCode = RequestUtil.getWarehouseCode();
        List<Long> idList = new ArrayList<>();
        for (Long id : ids) {
            OrderShippingUnitVo orderShippingUnit = orderShippingUnitService.selectByOrderId(id, OrderType.NORMAL);
            if (null != orderShippingUnit) {
                continue;
            }
            ShipDetail shipDetail = orderService.orderInitShipDetail(id, warehouseCode);
            if (null == shipDetail) {
                idList.add(id);
            }
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, idList);
    }

    //    @ApiOperation("修改订单备注")
//    @PostMapping("/note/update")
    public BaseResponse updateOrderNote(@RequestBody @Valid OrderNoteVo orderNoteVo) {
        String key = RedisKey.STRING_ORDER_NOTE + orderNoteVo.getOrderId();
        if (StringUtils.isBlank(orderNoteVo.getNote())) {
            redisTemplate.delete(key);
        } else {
            redisTemplate.opsForValue().set(key, orderNoteVo.getNote());
        }
        return BaseResponse.success();
    }

    @ApiOperation("取消订单")
    @PostMapping("/{id}/cancel")
    public BaseResponse cancelOrder(@PathVariable Long id) {
        Order order = new Order();
        order.setId(id);
        order.setPayState(-1);
        order.setShipPrice(BigDecimal.ZERO);
        order.setVatAmount(BigDecimal.ZERO);
        order.setServiceFee(BigDecimal.ZERO);
        order.setShipMethodId(0L);
        order.setUpdateTime(new Date());
        orderService.updateByPrimaryKeySelective(order);
        orderShippingUnitService.delByOrderId(id, OrderType.NORMAL);
        return BaseResponse.success();
    }


    @ApiOperation("批量取消")
    @PostMapping("/cancel")
    public BaseResponse cancelOrderByIds(@RequestBody List<Long> ids){
        orderService.cancelOrderByIds(ids);
        return BaseResponse.success();
    }

    @ApiOperation("恢复订单")
    @PostMapping("/{id}/restore")
    public BaseResponse restoreOrder(@PathVariable Long id) {
        Order order = orderService.selectByPrimaryKey(id);
        if (order.getPayState() == -1) {
            order = new Order();
            order.setId(id);
            order.setPayState(0);
            orderService.updateByPrimaryKeySelective(order);
            orderService.matchShipRule(id);
        } else {
            switch (order.getOrderType()) {
                case 2:
                    orderActionService.restoreSplitOrder(order);
                    break;
                case 3:
                    orderActionService.revertMergedOrder(order);
                    break;
            }
        }

        return BaseResponse.success();
    }

    //===============================admin===================================


    /**
     * 普通订单详情页
     */
    @ApiOperation("普通订单详情页")
    @RequestMapping(value = "/orderDetails/{id}", method = RequestMethod.POST)
    public BaseResponse orderDetails(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderService.orderDetails(id);
    }

    /**
     * 订单审核列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pendingList", method = RequestMethod.POST)
    public OrderListResponse pendingList(@RequestBody @Valid OrderListQueryRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderService.pendingList(request, session);
    }


    @ApiOperation("创建补发订单")
    @PostMapping("/createReshipOrder")
    public BaseResponse createReshipOrder(@RequestBody@Valid CreateReshipOrderRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse response  = orderService.createReshipOrder(request,session);
        if(response.getCode() == ResultCode.SUCCESS_CODE){
            Order order = (Order) response.getData();
            if (request.getNeedPay() && order.getShipMethodId() == null) {
                orderService.matchShipRule(order.getId());
            }else if (!request.getNeedPay()){
                orderService.importOrderToSaihe(order.getId());
            }
            return response;
        }
        return response;
    }

    @ApiOperation("订单添加产品")
    @PostMapping("/addItem")
    public BaseResponse orderAddItem(@RequestBody@Valid OrderAddItemRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse response = orderService.orderAddItem(request,session);
        if (response.getCode() == ResultCode.SUCCESS_CODE){
            orderService.initQuoteState(request.getOrderId());
            CompletableFuture<Void> updateProductAmount = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    List<Long> orderIds = new ArrayList<>();
                    orderIds.add(request.getOrderId());
                    orderService.initOrderProductAmount(orderIds);
                }
            },threadPoolExecutor);
            CompletableFuture<Void> initShip = CompletableFuture.runAsync(new Runnable() {
                @Override
                public void run() {
                    orderService.matchShipRule(request.getOrderId());
                }
            },threadPoolExecutor);
            try {
                CompletableFuture.allOf(updateProductAmount,initShip).get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 补发订单申请
     *
     * @param request
     * @return
     */
    @ApiOperation("补发订单申请")
    @RequestMapping(value = "/applyReshipOrder", method = RequestMethod.POST)
    public BaseResponse applyReshipOrder(@RequestBody @Valid ApplyReshipOrderRequest request) {
        String key = RedisUtil.KEY_ORDER_APPLY_RESHIP + request.getOriginalOrderId();
        boolean flag = RedisUtil.lock(redisTemplate, key, 2L, 1000L * 2 * 60);
        //获取锁成功
        if (!flag) {
            log.debug("获取锁失败:{}", key);
            return new BaseResponse(ResultCode.FAIL_CODE, "操作中...");
        }
        log.debug("获取锁:{}", key);
        try {
            Session session = UserUtil.getSession(redisTemplate);
            return orderService.applyReshipOrder(request, session);
        } catch (CustomerException e) {
            e.printStackTrace();
            return new BaseResponse(ResultCode.FAIL_CODE, e.getMessage());
        } finally {
            RedisUtil.unLock(redisTemplate, key);
            log.debug("释放锁:{}", key);
        }
    }

    /**
     * 补发启用
     *
     * @param request
     * @return
     */
    @ApiOperation("补发订单申请通过")
    @RequestMapping(value = "/confirmReshipOrder", method = RequestMethod.POST)
    public OrderUpdateResponse confirmPendingOrder(@RequestBody @Valid UpdatePendingOrderRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderService.confirmPendingOrder(request, session);
    }

    /**
     * 补发作废
     *
     * @param request
     * @return
     */
    @ApiOperation("补发订单申请驳回")
    @RequestMapping(value = "/cancelReshipOrder", method = RequestMethod.POST)
    public OrderUpdateResponse cancelPendingOrder(@RequestBody @Valid UpdatePendingOrderRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderService.cancelPendingOrder(request, session);
    }

    /**
     * admin历史订单列表
     *
     * @param request
     * @return
     */
    @ApiOperation("订单管理列表")
    @RequestMapping(value = "/manageList", method = RequestMethod.POST)
    public OrderListResponse historyList(@RequestBody AppOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return orderService.manageList(request, session);
    }

    /**
     * 导入赛盒  订单已经关联了赛盒运输方式 并且没有saiheOrderCode
     *
     * @return
     */
    @ApiOperation("订单导入赛盒")
    @RequestMapping(value = "/uploadSaihe", method = RequestMethod.POST)
    public BaseResponse importOrderToSaihe(@RequestBody OrderImportSaiheRequest request) {
        if (!SaiheConfig.SAIHE_ORDER_SWITCH) {
            return new BaseResponse(ResultCode.FAIL_CODE, "未开启");
        }
        String key = RedisUtil.KEY_ORDER_IMPORT_SAIHE + request.getOrderId();
        boolean flag = RedisUtil.lock(redisTemplate, key, 2L, 1000L * 2 * 60);
        //获取锁成功
        if (!flag) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            boolean res = orderService.importOrderToSaihe(request.getOrderId());
            if (res) {
                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
            }
            return new BaseResponse(ResultCode.FAIL_CODE, "导入失败!");
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE, e.getMessage());
        } finally {
            RedisUtil.unLock(redisTemplate, key);
        }
    }

    /**
     * 有赛盒orderCode 从赛盒获取物流
     *
     * @param request
     * @return
     */
    @ApiOperation("从赛盒获取物流")
    @RequestMapping(value = "/getTracking", method = RequestMethod.POST)
    public BaseResponse fromSaiheTracking(@RequestBody OrderSaiheTrackingRequest request) {
     /*   if (!SaiheConfig.SAIHE_ORDER_SWITCH) {
            return new BaseResponse(ResultCode.FAIL_CODE, "未开启");
        }*/
        String key = RedisUtil.KEY_ORDER_SAIHE_TRACK + request.getOrderId();
        boolean flag = RedisUtil.lock(redisTemplate, key, 2L, 1000L * 2 * 60);
        //获取锁成功
        if (!flag) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        Session session = UserUtil.getSession(redisTemplate);
        try {
            boolean res = orderService.getTrackingFromSaihe(request.getOrderId());
            if (res) {
                return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
            }
            return new BaseResponse(ResultCode.FAIL_CODE, "获取失败!");
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE, e.getMessage());
        } finally {
            RedisUtil.unLock(redisTemplate, key);
        }
    }

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 潘达信息 分页列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pageOrder", method = RequestMethod.POST)
    public BaseResponse upedgeOrderPage(@RequestBody PandaOrderListRequest request) {

        List<PandaOrderListVo> result = orderService.upedgeOrderPage(request);
        Long total = orderService.upedgeOrderCount(request);
        request.setTotal(total);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, result, request);
    }

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 订单分析 （echarts 选择时间区间 数量 和 地区分布）
     *
     * @param query
     * @return
     */
    @RequestMapping(value = "/orderAnalysis/quantityAndArea", method = RequestMethod.POST)
    public BaseResponse quantityAndArea(@RequestBody OrderAnalysisDto query) {
        HashMap resultrMap = orderService.quantityAndArea(query);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, resultrMap);
    }

    /**
     * 客户管理 -- 个人客户  -- 用户信息 -- 订单分析 （echarts 按月统计下单数和下单金额）
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/orderAnalysis/quantityAndAmount", method = RequestMethod.POST)
    public BaseResponse quantityAndAmount(@RequestBody OrderAnalysisDto request) throws ParseException {
        if (request == null || StringUtils.isBlank(request.getStartTime()) || StringUtils.isBlank(request.getCustomerId())) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL, "参数缺失");
        }
        Map resultMap = orderService.quantityAndAmount(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, resultMap);
    }

    /**
     * 查询订单list
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.POST)
    public BaseResponse orderList(@RequestBody PandaOrderListDto dto) {

        List<Order> resultList = orderService.getOrderList(dto);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, resultList);
    }

    /**
     * 根据年月  客户经理id  查询 该目标经理的  销售额、 下单客户数
     *
     * @param request
     * @return
     */
    @PostMapping("/ManagerActual")
    public BaseResponse getManagerActual(@RequestBody ManagerActualRequest request) {
        ManagerActualVo data = orderService.getManagerActual(request);

        return BaseResponse.success(data);
    }


    /**
     * 获取客户经理批发订单销售额
     *
     * @param allOrderAmountVo
     * @return
     */
//    @ApiOperation("获取客户经理订单销售额")
    @PostMapping("/amountByManagerCodeSet")
    public BaseResponse getNormalOrderAmountByManagerCodeSet(@RequestBody AllOrderAmountVo allOrderAmountVo) {
        return orderService.getNormalOrderAmountByManagerCodeSet(allOrderAmountVo);
    }

    ;

    //    @ApiOperation("获取某月普通订单下单客户数量  根据 set<managerCode> select")
    @PostMapping("/normalOrderCount")
    public BaseResponse getNormalOrderCount(@RequestBody AllOrderAmountVo allOrderAmountVo) {
        return orderService.getNormalOrderCount(allOrderAmountVo);
    }

    ;


    @PostMapping("customerOrderStatistical/{customerId}")
    public BaseResponse getCustomerOrderStatistical(@PathVariable Long customerId) {

        return orderService.getCustomerOrderStatistical(customerId);
    }

    @PostMapping("/updateWarehouse/{methodId}")
    public BaseResponse updateWarehouseByMethod(@PathVariable Long methodId){
        return orderService.updateOrderShippingWarehouse(methodId);
    }

    @PostMapping("/pullNormalTracking")
    public BaseResponse pullNormalTracking(){
        packageScheduler.pullNormalTracking();
        return BaseResponse.success();
    }
}
