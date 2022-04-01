package com.upedge.oms.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountOrderRefundedRequest;
import com.upedge.common.model.user.vo.CustomerAffiliateVo;
import com.upedge.common.model.user.vo.CustomerVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderItemDao;
import com.upedge.oms.modules.order.dao.OrderRefundDao;
import com.upedge.oms.modules.order.dao.OrderRefundItemDao;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.entity.OrderRefund;
import com.upedge.oms.modules.order.entity.OrderRefundItem;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.OrderRefundListResponse;
import com.upedge.oms.modules.order.service.OrderRefundItemService;
import com.upedge.oms.modules.order.service.OrderRefundService;
import com.upedge.oms.modules.order.vo.AppOrderItemVo;
import com.upedge.oms.modules.order.vo.OrderRefundVo;
import com.upedge.oms.modules.statistics.request.OrderRefundDailyCountRequest;
import com.upedge.oms.modules.statistics.service.OrderDailyRefundCountService;
import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.ApiCancelOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiOrderInfo;
import com.upedge.thirdparty.saihe.service.SaiheService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class OrderRefundServiceImpl implements OrderRefundService {

    @Autowired
    private OrderRefundDao orderRefundDao;
    @Autowired
    private OrderRefundItemDao orderRefundItemDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private UmsFeignClient umsFeignClient;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private OrderRefundItemService orderRefundItemService;
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    OrderDailyRefundCountService orderDailyRefundCountService;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderRefund record = new OrderRefund();
        record.setId(id);
        return orderRefundDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderRefund record) {
        return orderRefundDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderRefund record) {
        return orderRefundDao.insert(record);
    }

    /**
     *
     */
    public OrderRefund selectByPrimaryKey(Long id) {
        return orderRefundDao.selectByPrimaryKey(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse appApplyRefund(ApplyOrderRefundRequest request, Session session) {
        Long orderId = request.getOrderId();
        Order order = orderDao.selectByPrimaryKey(orderId);
        if (1 != order.getPayState() || 0 != order.getRefundState()) {
            return BaseResponse.failed();
        }
        if (request.getShippingPrice().compareTo(order.getShipPrice().add(order.getServiceFee())) > 0
                || request.getVatAmount().compareTo(order.getVatAmount()) > 0) {
            return BaseResponse.failed("The refund amount cannot be greater than the actual payment amount");
        }
        Long refundId = IdGenerate.nextId();
        BigDecimal refundAmount = BigDecimal.ZERO;
        BigDecimal refundProductAmount = BigDecimal.ZERO;
        List<OrderRefundItem> refundItems = request.getRefundItemList();
        if (ListUtils.isNotEmpty(refundItems)) {
            List<AppOrderItemVo> itemVos = orderItemDao.selectAppOrderItemByOrderId(orderId);
            Map<Long, AppOrderItemVo> itemMap = new HashMap<>();
            for (AppOrderItemVo itemVo : itemVos) {
                itemMap.put(itemVo.getId(), itemVo);
            }
            for (OrderRefundItem refundItem : refundItems) {
                AppOrderItemVo itemVo = itemMap.get(refundItem.getOrderItemId());
                if (null == itemVo || itemVo.getQuantity() < refundItem.getQuantity()) {
                    return BaseResponse.failed("The product refund quantity cannot be greater than the actual paid quantity");
                }
                refundProductAmount = new BigDecimal(refundItem.getQuantity()).multiply(itemVo.getPrice()).add(refundProductAmount);
                refundItem.setOrderId(orderId);
                refundItem.setRefundId(refundId);
                refundItem.setPrice(itemVo.getPrice());
                refundItem.setVariantImage(itemVo.getAdminVariantImage());
                refundItem.setVariantSku(itemVo.getStoreVariantSku());
            }
        }
        refundAmount = refundAmount.add(request.getShippingPrice()).add(request.getVatAmount()).add(refundProductAmount);
        BigDecimal payAmount = order.getPayAmount();
        if (refundAmount.compareTo(payAmount) > 0) {
            return BaseResponse.failed("The refund amount cannot be greater than the actual payment amount");
        }

        OrderRefund appRefund = new OrderRefund();
        appRefund.setId(refundId);
        appRefund.setOrderId(orderId);
        appRefund.setCustomerId(session.getCustomerId());
        appRefund.setReason(request.getRefundReason());
        appRefund.setRemark(request.getRemark());
        appRefund.setUpdateTime(new Date());
        appRefund.setCreateTime(new Date());
        appRefund.setRefundAmount(refundAmount);
        appRefund.setState(0);//申请中
        appRefund.setSource(session.getApplicationId());//来源app
        appRefund.setTrackingState(order.getShipState());
        appRefund.setRefundShippingPrice(request.getShippingPrice());
        appRefund.setRefundVatAmount(request.getVatAmount());
        appRefund.setRefundProductAmount(refundProductAmount);
        //修改订单状态  已付款的改为 退款中
        int res = orderDao.updateOrderAsRefunding(orderId);
        if (0 == res) {
            return BaseResponse.failed();
        }
        //添加退款申请
        orderRefundDao.insert(appRefund);
        if (refundItems.size() > 0) {
            //添加退款产品信息
            orderRefundItemDao.insertByBatch(refundItems);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(OrderRefund record) {
        return orderRefundDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(OrderRefund record) {
        return orderRefundDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<OrderRefund> select(Page<OrderRefund> record) {
        record.initFromNum();
        return orderRefundDao.select(record);
    }

    /**
     *
     */
    public long count(Page<OrderRefund> record) {
        return orderRefundDao.count(record);
    }

    /**
     * 退款申请列表
     *
     * @param request
     * @return
     */
    @Override
    public OrderRefundListResponse refundOrderList(OrderRefundListRequest request) {
        if (request.getT() == null) {
            request.setT(new OrderRefund());
        }
        OrderRefund orderRefund = request.getT();
        if (null == orderRefund.getState()) {
            orderRefund.setState(0);
        }
        request.initFromNum();
        List<OrderRefundVo> results = orderRefundDao.refundOrderList(request);
        for (OrderRefundVo result : results) {
            result.initMaxRefundAmount();
        }

        //  客户信息
        CompletableFuture<Void> customerFuture = CompletableFuture.runAsync(() -> {
            for (OrderRefundVo orderRefundVo : results) {
                BaseResponse customerResponse = umsFeignClient.customerInfo(orderRefundVo.getCustomerId());
                if (customerResponse.getCode() == ResultCode.SUCCESS_CODE) {
                    CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(customerResponse.getData()), CustomerVo.class);
                    if (customerVo != null) {
                        orderRefundVo.setCustomerLoginName(customerVo.getLoginName());
                        orderRefundVo.setCustomerName(customerVo.getUsername());
                    }
                }
            }
        }, threadPoolExecutor);

        // 客户经理信息
        CompletableFuture<Void> managerFuture = CompletableFuture.runAsync(() -> {
            for (OrderRefundVo orderRefundVo : results) {
                String managerCode = (String) redisTemplate.opsForHash().get(RedisKey.HASH_CUSTOMER_MANAGER_RELATE, orderRefundVo.getCustomerId().toString());
                orderRefundVo.setManagerCode(managerCode);
            }
        }, threadPoolExecutor);

        // 推荐人信息
        CompletableFuture<Void> affiliateFuture = CompletableFuture.runAsync(() -> {
            for (OrderRefundVo orderRefundVo : results) {
                BaseResponse customerAffiliateResponse = umsFeignClient.customerAffiliateInfo(orderRefundVo.getCustomerId());
                if (customerAffiliateResponse.getCode() == ResultCode.SUCCESS_CODE) {
                    CustomerAffiliateVo customerAffiliateVo = JSON.parseObject(JSON.toJSONString(customerAffiliateResponse.getData()), CustomerAffiliateVo.class);
                    if (customerAffiliateVo != null) {
                        orderRefundVo.setCustomerAffiliateName(customerAffiliateVo.getAffiliateName());
                    }
                }
            }
        }, threadPoolExecutor);

        // 获取订单的赛盒状态
        // OrderStatus
        //订单状态(正常 = 0,待审查 = 1,作废 = 2,锁定 = 3,只锁定发货 = 4,已完成 = 5)
        //OrderState
        //订单发货状态(未发货 = 0,部分发货 = 1,全部发货 = 2,妥投 = 3
        CompletableFuture<Void> saiheFuture = CompletableFuture.runAsync(() -> {
            for (OrderRefundVo orderRefundVo : results) {
                if (orderRefundVo.getSaiheOrderCode() != null) {
                    ApiGetOrderResponse apiGetOrderResponse = SaiheService.getOrderByCode(orderRefundVo.getSaiheOrderCode().toString());
                    if (apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")) {
                        List<ApiOrderInfo> l = apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
                        if (l != null && l.size() > 0) {
                            Integer orderState = l.get(0).getOrderState();
                            Integer orderStatus = l.get(0).getOrderStatus();
                            orderRefundVo.setSaiheOrderState(orderState);
                            orderRefundVo.setSaiheorderStatus(orderStatus);
                        }
                    }
                }
            }
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(customerFuture,managerFuture,affiliateFuture,saiheFuture).get();
        } catch (Exception e) {
            e.printStackTrace();
            return (OrderRefundListResponse) OrderRefundListResponse.failed();
        }
        Long total = orderRefundDao.refundOrderCount(request);
        request.setTotal(total);
        return new OrderRefundListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }

    /**
     * 申请订单退款
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public BaseResponse applyRefund(ApplyOrderRefundRequest request, Session session) throws CustomerException {
        Long orderId = request.getOrderId();
        String refundReason = request.getRefundReason();
        BigDecimal refundShippingPrice = request.getShippingPrice();
        BigDecimal refundVatAmount = request.getVatAmount();
        String remark = request.getRemark();
        List<OrderRefundItem> refundItemList = request.getRefundItemList();
        if (refundVatAmount == null || refundVatAmount.compareTo(BigDecimal.ZERO) < 0
                || refundShippingPrice == null || refundShippingPrice.compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
        }
        Order appPandaOrder = orderDao.selectByPrimaryKey(orderId);
        if (appPandaOrder == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单不存在!");
        }
        if (appPandaOrder.getPayState() != 1 || appPandaOrder.getRefundState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单状态不满足退款条件!");
        }
        if (appPandaOrder.getOrderType() == 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "补发订单不能退款!");
        }
        BigDecimal refundAmount = BigDecimal.ZERO;
        BigDecimal refundProductAmount = BigDecimal.ZERO;
        refundAmount = refundAmount.add(refundShippingPrice).add(refundVatAmount);
        //检查退款产品
        Long refundId = IdGenerate.nextId();
        for (OrderRefundItem refundItem : refundItemList) {
            if (refundItem.getQuantity() == null || refundItem.getQuantity() <= 0) {
                return new BaseResponse(ResultCode.FAIL_CODE, "数量异常!");
            }
            if (refundItem.getOrderItemId() == null) {
                return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
            }
            if (StringUtils.isBlank(refundItem.getVariantImage())
                    || StringUtils.isBlank(refundItem.getVariantSku())) {
                return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
            }
            refundItem.setOrderId(orderId);
            OrderItem old = orderItemDao.queryOrderItemByIdAndOrderId(refundItem.getOrderItemId(), orderId);
            if (old == null) {
                return new BaseResponse(ResultCode.FAIL_CODE, "参数异常!");
            }
            if (refundItem.getQuantity() > old.getQuantity()) {
                return new BaseResponse(ResultCode.FAIL_CODE, "产品数量超过限制!");
            }
            refundProductAmount = refundProductAmount.add(old.getUsdPrice().
                    multiply(new BigDecimal(refundItem.getQuantity())));
            refundItem.setPrice(old.getUsdPrice());
            refundItem.setRefundId(refundId);
        }
        if (refundShippingPrice.compareTo(BigDecimal.ZERO) <= 0 && refundProductAmount.compareTo(BigDecimal.ZERO) <= 0
                && refundVatAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "无效退款!");
        }
        if (refundProductAmount.compareTo(appPandaOrder.getProductAmount()) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款产品金额超过限制!");
        }
        refundAmount = refundAmount.add(refundProductAmount);
        if (refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款总金额异常!");
        }
        BigDecimal vatAmount = appPandaOrder.getVatAmount() == null ? BigDecimal.ZERO : appPandaOrder.getVatAmount();
        if (refundVatAmount.compareTo(vatAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款VAT税费超过限制!");
        }
        if (refundShippingPrice.compareTo(appPandaOrder.getShipPrice()) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款运费超过限制!");
        }
        //可退最大金额
        BigDecimal maxAmount = appPandaOrder.getShipPrice().
                add(appPandaOrder.getProductAmount()).add(vatAmount);
        if (refundAmount.compareTo(maxAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "申请退款金额大于可退金额!");
        }
        //申请退款
        //String userCode=String.valueOf(session.getId());

        OrderRefund appRefund = new OrderRefund();
        appRefund.setId(refundId);
        appRefund.setOrderId(orderId);
        appRefund.setCustomerId(appPandaOrder.getCustomerId());
        appRefund.setManagerCode(session.getLoginname());
        appRefund.setReason(refundReason);
        appRefund.setRemark(remark);
        appRefund.setUpdateTime(new Date());
        appRefund.setCreateTime(new Date());
        appRefund.setRefundAmount(refundAmount);
        appRefund.setState(0);//申请中
        appRefund.setSource(session.getApplicationId());//来源admin
        appRefund.setTrackingState(appPandaOrder.getShipState());
        appRefund.setRefundShippingPrice(refundShippingPrice);
        appRefund.setRefundVatAmount(refundVatAmount);
        appRefund.setRefundProductAmount(refundProductAmount);
        //修改订单状态  已付款的改为 退款中
        int res = orderDao.updateOrderAsRefunding(orderId);
        if (res == 0) {
            throw new CustomerException("订单状态过期,申请订单退款失败");
        }
        //添加退款申请
        orderRefundDao.insert(appRefund);
        if (refundItemList.size() > 0) {
            //添加退款产品信息
            orderRefundItemDao.insertByBatch(refundItemList);
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, "申请退款成功!",appRefund);
    }

    /**
     * 更新退款备注
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public BaseResponse updateRemark(OrderRefundUpdateRemarkRequest request) {
        OrderRefund orderRefund = orderRefundDao.selectByPrimaryKey(request.getId());
        if (orderRefund == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "更新备注失败！");
        }
        orderRefundDao.updateRemark(request.getId(), request.getRemark());
        return new BaseResponse(ResultCode.SUCCESS_CODE, "更新备注成功！");
    }

    /**
     * 驳回退款申请
     *
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public BaseResponse rejectRefund(OrderRefundRejectRefundRequest request, Session session) throws CustomerException {
        OrderRefund orderRefund = orderRefundDao.selectByPrimaryKey(request.getId());
        if (orderRefund == null || orderRefund.getState() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "参数异常！");
        }
        //0:申请中 1:确认退款 2:已驳回
        if (orderRefund.getState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "状态过时请刷新！");
        }
        Order order = orderDao.selectByPrimaryKey(orderRefund.getOrderId());
        //pay_state 支付状态,待支付=0，已支付=1，取消订单=-1，支付中=2
        //refund_state 退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if (order == null || order.getPayState() == null || order.getPayState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单状态异常！");
        }
        if (order.getRefundState() == null || order.getRefundState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款状态异常！");
        }
        //0:申请中 1:确认退款 2:已驳回
        orderRefund.setState(2);
        orderRefund.setRejectInfo(request.getRejectInfo());
        orderRefund.setManagerCode(session.getLoginname());
        orderRefund.setUpdateTime(new Date());
        int res = orderRefundDao.rejectRefund(orderRefund);
        if (res == 0) {
            throw new CustomerException(ResultCode.FAIL_CODE, "驳回失败!");
        }
        //订单状态改为 已付款
        int rs = orderDao.restoreOrderRefundState(order.getId());
        if (rs == 0) {
            throw new CustomerException(ResultCode.FAIL_CODE, "驳回失败!");
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, "驳回成功！");
    }

    /**
     * 确认退款
     */
    @Override
    @GlobalTransactional//分布式事务注解
    public BaseResponse confirmRefund(ConfirmRefundRequest request, Session session) throws CustomerException {
        //确认退款
        OrderRefund orderRefund = orderRefundDao.selectByPrimaryKey(request.getId());
        if (orderRefund == null || orderRefund.getState() == null
                || orderRefund.getCustomerId() == null || orderRefund.getOrderId() == null ||
                orderRefund.getRefundAmount() == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "参数异常");
        }
        //退款状态，申请中=0，通过=1，驳回=2
        //订单状态为退款中
        if (orderRefund.getState() != 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "状态过时请刷新");
        }

        //查询订单
        Long orderId = orderRefund.getOrderId();
        Order order = orderDao.selectByPrimaryKey(orderId);
        if (order == null) {
            return new BaseResponse(ResultCode.FAIL_CODE, "找不到订单");
        }
        //支付状态,待支付=0，已支付=1，取消订单=-1
        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        if (order.getPayState() == null || order.getPayState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单支付状态异常");
        }
        if (order.getRefundState() == null || order.getRefundState() != 1) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单退款状态异常");
        }
        if (order.getPayAmount() == null || order.getPayAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "订单金额异常");
        }

        //检查申请退款金额
        BigDecimal actualRefundAmount = request.getActualRefundAmount();
        BigDecimal refundAmount = orderRefund.getRefundAmount();
        if (actualRefundAmount.compareTo(refundAmount) != 0){
            refundAmount = actualRefundAmount;
        }
        if (refundAmount == null || refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款金额异常");
        }
        //检查申请退款金额  不能大于支付总金额  支付商品总金额/USD+运费/USD+VAT税费
        BigDecimal shippingPrice = order.getShipPrice() == null ? BigDecimal.ZERO : order.getShipPrice().add(order.getServiceFee());
        BigDecimal vatAmount = order.getVatAmount() == null ? BigDecimal.ZERO : order.getVatAmount();
        BigDecimal productAmount = order.getProductAmount() == null ? BigDecimal.ZERO : order.getProductAmount();
        BigDecimal totalAmount = shippingPrice.add(vatAmount).add(productAmount);
        if (refundAmount.compareTo(totalAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款金额超过可退金额");
        }

        BigDecimal refundShippingPrice = orderRefund.getRefundShippingPrice();
        if (refundShippingPrice == null || refundShippingPrice.compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款运费金额异常");
        }
        if (refundShippingPrice.compareTo(shippingPrice) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款运费金额超出限制");
        }
        BigDecimal refundVatAmount = orderRefund.getRefundVatAmount();
        if (refundVatAmount == null || refundVatAmount.compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款VAT金额异常");
        }
        if (refundVatAmount.compareTo(vatAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款VAT金额超出限制");
        }
        BigDecimal refundProductAmount = orderRefund.getRefundProductAmount();
        if (refundProductAmount == null || refundProductAmount.compareTo(BigDecimal.ZERO) < 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款产品金额异常");
        }
        if (refundProductAmount.compareTo(productAmount) > 0) {
            return new BaseResponse(ResultCode.FAIL_CODE, "退款产品金额超出限制");
        }

        Integer state = 2;//AppStockOrderEnum.PARTREFUNDED.getValue();
        //与可退金额做对比
        if (orderRefund.getRefundAmount().compareTo(totalAmount) == 0) {
            state = 3;//AppStockOrderEnum.REFUNDED.getValue();
        }

        //退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
        //修改订单状态
        int res = orderDao.updateOrderAsRefund(order.getId(), state);
        updateConfirmAppRefundTrackingState(orderRefund.getId());
        if (res == 0) {
            throw new CustomerException(ResultCode.FAIL_CODE, "修改订单状态异常");
        }
        //更新状态 已确认
        orderRefund.setState(1);
        //   String userCode=String.valueOf(session.getId());
        orderRefund.setActualRefundAmount(actualRefundAmount);
        orderRefund.setUpdateTime(new Date());

        int rs = orderRefundDao.updateConfirmRefund(orderRefund);
        if (rs == 0) {
            throw new CustomerException(ResultCode.FAIL_CODE, "修改退款单状态异常");
        }
        // 统计退款信息
        OrderRefundDailyCountRequest orderRefundDailyCountRequest = new OrderRefundDailyCountRequest();
        orderRefundDailyCountRequest.setRefundId(request.getId());
        orderRefundDailyCountRequest.setOrderType(OrderType.NORMAL);
        orderRefundDailyCountRequest.setRefundTime(new Date());
        orderDailyRefundCountService.OrderDailyRefundCount(orderRefundDailyCountRequest);

        AccountOrderRefundedRequest accountOrderRefundedRequest = new AccountOrderRefundedRequest();
        accountOrderRefundedRequest.setOrderId(order.getId());
        accountOrderRefundedRequest.setRefundId(request.getId());
        accountOrderRefundedRequest.setCustomerId(order.getCustomerId());
        //支付商品总金额/USD+运费/USD+VAT税费
        accountOrderRefundedRequest.setPayAmount(order.getPayAmount());
        accountOrderRefundedRequest.setRefundAmount(refundAmount);
        accountOrderRefundedRequest.setOrderType(OrderType.NORMAL);
        //账户退款
        BaseResponse response = umsFeignClient.accountOrderRefunded(accountOrderRefundedRequest);
        if (response.getCode() != 1) {
            throw new CustomerException(ResultCode.FAIL_CODE, response.getMsg());
        }
        //作废赛盒订单，并同步赛盒发货状态
        cancelSaiheOrderAndSynState(orderRefund.getId(), order.getSaiheOrderCode());
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    //更新赛盒状态
    //根据id同步退款发货状态
    @Transactional(readOnly = false)
    public void updateConfirmAppRefundTrackingState(Long id) {
        //查询已确认退款的赛盒状态
        OrderRefund appRefund = orderRefundDao.selectByPrimaryKey(id);
        Order order = orderDao.selectByPrimaryKey(appRefund.getOrderId());
        //获取订单的赛盒code
        if (appRefund != null && !StringUtils.isBlank(order.getSaiheOrderCode())) {
            ApiGetOrderResponse apiGetOrderResponse = SaiheService.getOrderByCode(order.getSaiheOrderCode());
            if (apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")) {
                List<ApiOrderInfo> l = apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
                if (l != null && l.size() > 0) {
                    if (l != null && l.size() > 0) {
                        //赛盒发货状态  订单发货状态(未发货 = 0,部分发货 = 1,全部发货 = 2,妥投 = 3)
                        Integer orderState = l.get(0).getOrderState();
                        Integer orderSourceId = l.get(0).getOrderSourceID();
                        if (orderState > 0) {
                            orderRefundDao.updateConfirmAppRefundTrackingState(appRefund.getId(), 1, orderSourceId);
                        } else {
                            orderRefundDao.updateConfirmAppRefundTrackingState(appRefund.getId(), 0, orderSourceId);
                        }
                    }
                }
            }
        }
    }

    /**
     * 取消赛盒订单，并同步发货状态
     */
    @Override
    public void cancelSaiheOrderAndSynState(Long refundId, String saiheOrderCode) throws CustomerException {
        Integer orderState = null;
        Integer orderSourceId = null;
        //赛盒未发货 取消订单
        //获取订单的赛盒code
        if (!StringUtils.isBlank(saiheOrderCode)) {
            ApiGetOrderResponse apiGetOrderResponse = SaiheService.getOrderByCode(saiheOrderCode);
            if (apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")) {
                List<ApiOrderInfo> l = apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
                if (l != null && l.size() > 0) {
                    if (l != null && l.size() > 0) {
                        //赛盒发货状态 orderState 订单发货状态(未发货 = 0,部分发货 = 1,全部发货 = 2,妥投 = 3)
                        //订单状态OrderStatus(正常 = 0,待审查 = 1,作废 = 2,锁定 = 3,只锁定发货 = 4,已完成 = 5)
                        orderState = l.get(0).getOrderState();
                        Integer orderStatus = l.get(0).getOrderStatus();
                        orderSourceId = l.get(0).getOrderSourceID();
                        //赛盒未发货
                        if (orderState == 0) {
                            //订单已作废
                            if (orderStatus == 2) {
                                //message.append("赛盒未发货,订单已作废!</br> ");
                            } else {
                                //作废订单
                                //作废成功
                                ApiCancelOrderResponse apiCancelOrderResponse = SaiheService.cancelOrderInfo(saiheOrderCode);
                                if (apiCancelOrderResponse.getCancelOrderResult().getStatus().equals("OK") &&
                                        apiCancelOrderResponse.getCancelOrderResult().getSuccess()) {
                                    //message.append("赛盒未发货,订单作废成功!</br> ");
                                } else {
                                    //作废失败
                                    //throw newValidationException("赛盒未发货,订单作废失败!</br> ");
                                    throw new CustomerException(ResultCode.FAIL_CODE, apiCancelOrderResponse.getCancelOrderResult().getMsg());
                                }
                            }
                        }
                    }
                }
            }
        }

        //同步退款发货状态
        if (orderState != null && orderSourceId != null) {
            if (orderState > 0) {
                orderRefundDao.updateConfirmAppRefundTrackingState(refundId, 1, orderSourceId);
            } else {
                orderRefundDao.updateConfirmAppRefundTrackingState(refundId, 0, orderSourceId);
            }
        }

    }

    /**
     * 历史退款列表
     *
     * @param request
     * @return
     */
    @Override
    public OrderRefundListResponse refundOrderHistory(OrderRefundListRequest request) {
        if (request.getT() == null) {
            request.setT(new OrderRefund());
        }
        OrderRefund orderRefund = request.getT();
        //0:申请中 1:确认退款 2:已驳回
        orderRefund.setGteState(1);
        request.initFromNum();
        List<OrderRefundVo> results = orderRefundDao.refundOrderList(request);
        for (OrderRefundVo result : results) {
            // 客户信息和推荐人信息
            if (result.getCustomerId() != null) {
                BaseResponse customerInfoResponse = umsFeignClient.customerInfo(result.getCustomerId());
                BaseResponse customerAffiliateInfoResponse = umsFeignClient.customerAffiliateInfo(result.getCustomerId());
                if (customerInfoResponse.getCode() == ResultCode.SUCCESS_CODE) {
                    CustomerVo customerVo = JSON.parseObject(JSON.toJSONString(customerInfoResponse.getData()), CustomerVo.class);
                    if (customerVo != null) {
                        result.setCustomerLoginName(customerVo.getLoginName());
                        result.setCustomerName(customerVo.getUsername());
                    }
                }
                if (customerAffiliateInfoResponse.getCode() == ResultCode.SUCCESS_CODE) {
                    CustomerAffiliateVo customerAffiliateVo = JSON.parseObject(JSON.toJSONString(customerAffiliateInfoResponse.getData()), CustomerAffiliateVo.class);
                    if (customerAffiliateVo != null) {
                        result.setReferrerLoginName(customerAffiliateVo.getAffiliateLoginName());
                    }
                }
            }

            // 退款订单项信息
            result.setOrderRefundItemList(orderRefundItemService.selectOrderRefundItemListbByRefundId(result.getId()));

        }
        Long total = orderRefundDao.refundOrderCount(request);
        request.setTotal(total);
        return new OrderRefundListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
    }
}