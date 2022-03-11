package com.upedge.oms.modules.stock.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.*;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalOrder.PaypalOrderItem;
import com.upedge.common.model.log.MqMessageLog;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.order.request.CustomerOrderDailyCountUpdateRequest;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.VariantDetail;
import com.upedge.common.model.product.VariantQuantity;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.oms.constant.StockOrderState;
import com.upedge.oms.modules.cart.dao.CartDao;
import com.upedge.oms.modules.cart.entity.Cart;
import com.upedge.oms.modules.order.entity.OrderTracking;
import com.upedge.oms.modules.order.service.OrderTrackingService;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.stock.dao.CustomerProductStockDao;
import com.upedge.oms.modules.stock.dao.CustomerStockRecordDao;
import com.upedge.oms.modules.stock.dao.StockOrderDao;
import com.upedge.oms.modules.stock.dao.StockOrderItemDao;
import com.upedge.oms.modules.stock.dto.StockOrderItemPurchaseNoDto;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.entity.CustomerStockRecord;
import com.upedge.oms.modules.stock.entity.StockOrder;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import com.upedge.oms.modules.stock.request.StockOrderItemUpdatePurchaseNoRequest;
import com.upedge.oms.modules.stock.request.StockOrderListRequest;
import com.upedge.oms.modules.stock.request.StockOrderUpdateShipRequest;
import com.upedge.oms.modules.stock.request.StockOrderUpdateTrackRequest;
import com.upedge.oms.modules.stock.service.StockOrderService;
import com.upedge.oms.modules.stock.vo.StockOrderItemVo;
import com.upedge.oms.modules.stock.vo.StockOrderVo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class StockOrderServiceImpl implements StockOrderService {

    @Autowired
    private StockOrderDao stockOrderDao;

    @Autowired
    StockOrderItemDao stockOrderItemDao;

    @Autowired
    CustomerProductStockDao customerProductStockDao;

    @Autowired
    CustomerStockRecordDao customerStockRecordDao;

    @Autowired
    CartDao cartDao;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private OrderTrackingService orderTrackingService;

    @Autowired
    OrderDailyPayCountService orderDailyPayCountService;

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(Long id) {
        StockOrder record = new StockOrder();
        record.setId(id);
        return stockOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(StockOrder record) {
        return stockOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertSelective(StockOrder record) {
        return stockOrderDao.insert(record);
    }

    @Override
    public BaseResponse confirmShipReview(Long orderId, Session session) {
        StockOrder stockOrder = selectByPrimaryKey(orderId);
        if (stockOrder == null
                || stockOrder.getShipReview() != 2
                || stockOrder.getPayState() != 1) {
            return BaseResponse.failed("订单不存在或订单未支付");
        }
        int i = stockOrderDao.confirmShipReview(orderId);
        if (i == 1) {
            return BaseResponse.success();
        }
        return BaseResponse.failed("订单异常");
    }

    @Override
    public BaseResponse updateTrack(StockOrderUpdateTrackRequest request, Session session) {
        Long orderId = request.getOrderId();
        StockOrder stockOrder = selectByPrimaryKey(orderId);
        if (stockOrder == null
                || stockOrder.getShipReview() != 3
                || stockOrder.getPayState() != 1) {
            return BaseResponse.failed("订单未支付或运费为审核");
        }
        OrderTracking orderTracking = orderTrackingService.queryOrderTrackingByOrderId(orderId, OrderType.STOCK);
        if (null == orderTracking) {
            orderTracking = new OrderTracking();
            orderTracking.setTrackingCompany(stockOrder.getShipMethod());
            orderTracking.setOrderId(orderId);
            orderTracking.setCreateTime(new Date());
            orderTracking.setUpdateTime(new Date());
            orderTracking.setOrderTrackingType(OrderType.STOCK);
            orderTracking.setTrackingCode(request.getTrackingCode());
            orderTracking.setShippingMethodName(stockOrder.getShipMethod());
            orderTracking.setTrackType(request.getTrackingType());
            orderTracking.setState(1);
            orderTrackingService.insert(orderTracking);
        } else {
            orderTracking.setTrackType(request.getTrackingType());
            orderTracking.setTrackingCode(request.getTrackingCode());
            orderTracking.setUpdateTime(new Date());
            orderTrackingService.updateOrderTracking(orderTracking);
        }
        return BaseResponse.success();
    }

    @Transactional
    @Override
    public BaseResponse orderConfirmReceipt(Long orderId, Session session) {
        String key = "stock:receipt:" + orderId;
        boolean b = RedisUtil.lock(redisTemplate, key, 10L, 30 * 1000L);
        if (!b) {
            return BaseResponse.failed();
        }
        StockOrder stockOrder = selectByPrimaryKey(orderId);
        if (stockOrder == null
                || stockOrder.getShipReview() != 3
                || stockOrder.getPayState() != 1) {
            return BaseResponse.failed("订单未支付或运费为审核");
        }
        orderPaidByPaymentId(orderId, stockOrder.getCustomerId(), stockOrder.getWarehouseCode());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse rejectShipReview(Long orderId, Session session) {
        return null;
    }

    @Override
    public BaseResponse updateShipDetail(Session session, StockOrderUpdateShipRequest request) {
        Long orderId = request.getOrderId();
        StockOrder stockOrder = selectByPrimaryKey(orderId);
        if (stockOrder == null
                || stockOrder.getShipReview() > 1
                || stockOrder.getPayState() != 0) {
            return BaseResponse.failed("订单不存在或订单已支付");
        }
        stockOrder.setShipReview(1);
        stockOrder.setShipMethod(request.getShipMethod());
        stockOrder.setShipPrice(request.getShipPrice());
        stockOrderDao.updateByPrimaryKeySelective(stockOrder);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse excelImportStockOrders(Session session, List<VariantQuantity> variantQuantities) {
        List<String> variantSkus = new ArrayList<>();
        Map<String, Integer> variantQuantity = new HashMap<>();
        variantQuantities.forEach(v -> {
            variantQuantity.put(v.getVariantSku(), v.getQuantity());
            variantSkus.add(v.getVariantSku());
        });

        ListVariantsRequest listVariantsRequest = new ListVariantsRequest();
        listVariantsRequest.setVariantSkus(variantSkus);
        BaseResponse response = pmsFeignClient.listVariantDetailByIds(listVariantsRequest);
        if (ResultCode.FAIL_CODE == response.getCode()
                || null == response.getData()) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }
        Date date = new Date();
        List<Cart> carts = new ArrayList<>();
        List<VariantDetail> variantDetails = JSONArray.parseArray(JSON.toJSONString(response.getData())).toJavaList(VariantDetail.class);
        variantDetails.forEach(variantDetail -> {
            Cart cart = new Cart(variantDetail, date);
            cart.setId(IdGenerate.nextId());
            cart.setCustomerId(session.getCustomerId());
            cart.setQuantity(variantQuantity.get(variantDetail.getVariantSku()));
            cart.setCartType(0);
            carts.add(cart);
        });
        if (ListUtils.isNotEmpty(carts)) {
            cartDao.insertByBatch(carts);
            return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, carts);
        }
        return BaseResponse.failed();
    }

    @Override
    public BaseResponse createPaypalOrder(List<Long> ids, Session session) {
        Long paymentId = IdGenerate.nextId();

        int i = stockOrderDao.updatePaymentIdByIds(paymentId, ids);
        if (i != ids.size()) {
            return BaseResponse.failed("The order information is incorrect");
        }

        BigDecimal amount = stockOrderDao.selectAmountByPaymentId(paymentId);

        PaypalOrder paypalOrder = new PaypalOrder();
        paypalOrder.setId(paymentId);
        paypalOrder.setAccountId(session.getAccountId());
        paypalOrder.setOrderType(OrderType.STOCK);
        paypalOrder.setSession(session);
        HttpServletRequest request = RequestUtil.getRequest();
        String origin = request.getHeader("Origin");
        paypalOrder.setSuccessUrl(origin + "/payment/stockPayPal");
        paypalOrder.setFailedUrl(origin + "/payment/stockPayPal");
        paypalOrder.setAccountPaymethodId(PayOrderMethod.PAYPAL);
        paypalOrder.setAmount(amount);
        paypalOrder.setProductAmount(amount);

        List<PaypalOrderItem> orderItems = new ArrayList<>();

        List<StockOrderVo> orderVos = stockOrderDao.selectOrderByIds(ids);
        orderVos.forEach(stockOrderVo -> {
            List<StockOrderItemVo> orderItemVos = stockOrderVo.getItems();
            orderItemVos.forEach(itemVo -> {
                PaypalOrderItem orderItem = new PaypalOrderItem();
                orderItem.setItemId(itemVo.getId());
                orderItem.setOrderId(itemVo.getOrderId());
                orderItem.setPrice(itemVo.getPrice());
                orderItem.setQuantity(itemVo.getQuantity());
                orderItem.setSku(itemVo.getVariantSku());
                orderItems.add(orderItem);
            });
        });
        paypalOrder.setItems(orderItems);

        BaseResponse response = umsFeignClient.getPaypalOrderUrl(paypalOrder);

        if (response.getCode() == ResultCode.SUCCESS_CODE) {
            stockOrderDao.updatePayStateByPaymentId(paymentId, StockOrderState.PENDING);
        }

        return response;
    }

    @Override
    public List<StockOrderVo> selectOrderByIds(List<Long> ids) {
        return stockOrderDao.selectOrderByIds(ids);
    }

    @Override
    public StockOrderVo selectOrderById(Long orderId) {
        return stockOrderDao.selectOrderById(orderId);
    }

    @Transactional
    @Override
    public BaseResponse updateOrderItemPurchaseNo(StockOrderItemUpdatePurchaseNoRequest request) {
        StockOrder stockOrder = stockOrderDao.selectByPrimaryKey(request.getOrderId());
        if (null == stockOrder
                || OrderConstant.PAY_STATE_PAID != stockOrder.getPayState()
                || OrderConstant.NO_REFUND != stockOrder.getPayState()
                || stockOrder.PURCHASING != stockOrder.getPurchaseState()) {
            return BaseResponse.failed("订单状态异常");
        }
        List<StockOrderItemPurchaseNoDto> stockOrderItemPurchaseNoDtos = request.getItemPurchaseNos();
        if (ListUtils.isEmpty(stockOrderItemPurchaseNoDtos)) {
            return BaseResponse.failed();
        }
        for (StockOrderItemPurchaseNoDto stockOrderItemPurchaseNoDto : stockOrderItemPurchaseNoDtos) {
            StockOrderItem stockOrderItem = new StockOrderItem();
            stockOrderItem.setId(stockOrderItemPurchaseNoDto.getItemId());
            stockOrderItem.setPurchaseNo(stockOrderItemPurchaseNoDto.getPurchaseNo());
            stockOrderItemDao.updateByPrimaryKeySelective(stockOrderItem);
        }
        return BaseResponse.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean refreshOrderDetail(Long orderId) {
        StockOrder order = stockOrderDao.selectByPrimaryKey(orderId);
        if (StockOrderState.UN_PAID != order.getPayState()) {
            return false;
        }

        stockOrderDao.updateOrderAmountById(orderId);

        return true;
    }

    @Override
    public int updateOrderPayState(StockOrder order) {
        return stockOrderDao.updateOrderPayState(order);
    }

    @Override
    public void updatePriceByVariantId(VariantDetail variantDetail) {
        if (null != variantDetail.getUsdPrice()) {
            stockOrderItemDao.updatePriceByVariantId(variantDetail.getVariantId(), variantDetail.getUsdPrice());
        }
    }

    @Override
    public List<StockOrderVo> selectOrderList(StockOrderListRequest request) {
        List<StockOrderVo> stockOrderVos = stockOrderDao.selectOrderList(request);
        if (ListUtils.isEmpty(stockOrderVos)) {
            return new ArrayList<>();
        }
        List<Long> orderIds = new ArrayList<>();
        for (StockOrderVo stockOrderVo : stockOrderVos) {
            orderIds.add(stockOrderVo.getId());
        }
        List<StockOrderItemVo> stockOrderItemVos = stockOrderItemDao.selectItemVoByOrderIds(orderIds);
        for (StockOrderVo stockOrderVo : stockOrderVos) {
            List<StockOrderItemVo> storeOrderItemVoList = new ArrayList<>();
            Long orderId = stockOrderVo.getId();
            for (StockOrderItemVo stockOrderItemVo : stockOrderItemVos) {
                if (orderId.equals(stockOrderItemVo.getOrderId())) {
                    storeOrderItemVoList.add(stockOrderItemVo);
                }
            }
            stockOrderVo.setItems(storeOrderItemVoList);
            stockOrderItemVos.removeAll(storeOrderItemVoList);
        }
        return stockOrderVos;
    }

    @Override
    public Long countOrderList(StockOrderListRequest request) {
        return stockOrderDao.countOrderList(request);
    }

    @Override
    public int updatePayStateByPaymentId(Long paymentId, Integer payState) {
        return stockOrderDao.updatePayStateByPaymentId(paymentId, payState);
    }

    @Transactional
    @Override
    public PaymentDetail orderPaidByPaypal(Long paymentId, Long customerId, Long userId) {
        Date payTime = new Date();
        BigDecimal amount = stockOrderDao.selectAmountByPaymentId(paymentId);
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(paymentId);
        detail.setUserId(userId);
        detail.setCustomerId(customerId);
        detail.setPayMethod(PayOrderMethod.PAYPAL);
        detail.setPayAmount(amount);
        detail.setOrderType(OrderType.STOCK);
        detail.setPayTime(payTime);
        stockOrderDao.completeOrderTransaction(detail, Constant.PAYPAL_FEE_PERCENTAGE);
//        orderPaidByPaymentId(paymentId,null, customerId);
        return detail;
    }

    @Override
    public void payOrderAsync(PaymentDetail detail) {
        threadPoolExecutor.submit(() -> {
            sendSavePaymentDetailMessage(detail);
           /* CustomerOrderDailyCountUpdateRequest customerOrderDailyCountUpdateRequest = new CustomerOrderDailyCountUpdateRequest();
            customerOrderDailyCountUpdateRequest.setCustomerId(session.getCustomerId());
            customerOrderDailyCountUpdateRequest.setOrderType(TransactionConstant.OrderType.STOCK_ORDER.getCode());
            customerOrderDailyCountUpdateRequest.setPaymentId(paymentId);
            customerOrderDailyCountUpdateRequest.setPayTime(payTime);
            redisTemplate.opsForList().leftPush(RedisKey.LIST_CUSTOMER_ORDER_DAILY_COUNT_UPDATE,customerOrderDailyCountUpdateRequest);*/
        });
    }

    @GlobalTransactional
    @Override
    public PaymentDetail payOrderByBalance(Long id, Session session) {
        StockOrder stockOrder = selectByPrimaryKey(id);
        if (stockOrder == null
                || stockOrder.getPayState() != 0
                || stockOrder.getShipReview() != 1) {
            return null;
        }
        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + stockOrder.getWarehouseCode());
        if (warehouseVo == null) {
            return null;
        }
        //修改订单支付状态
        BigDecimal amount = stockOrder.getAmount();
        Date payTime = new Date();
        Long paymentId = IdGenerate.nextId();
        stockOrder = new StockOrder();
        stockOrder.setId(id);
        stockOrder.setPayTime(payTime);
        stockOrder.setPaymentId(paymentId);
        stockOrder.setPayMethod(0);
        stockOrder.setPayState(1);
        //海外仓的订单需要先审核运费
        if (warehouseVo.getWarehouseType() == WarehouseVo.LOCAL) {
            stockOrder.setShipReview(3);
        } else {
            stockOrder.setShipReview(2);
        }
        updateByPrimaryKeySelective(stockOrder);

        //用户模块账户扣款
        AccountPaymentRequest request = new AccountPaymentRequest();
        request.setAccountId(session.getAccountId());
        request.setCustomerId(session.getCustomerId());
        request.setFixFee(BigDecimal.ZERO);
        request.setOrderType(OrderType.STOCK);
        request.setPayType(PayOrderMethod.RECHARGE);
        request.setUserId(session.getId());
        request.setId(paymentId);
        request.setPayAmount(amount);
        BaseResponse response = umsFeignClient.accountPayment(request);
        if (response.getCode() != ResultCode.SUCCESS_CODE) {
            return null;
        }
        //本地仓更新库存信息
        if (warehouseVo.getWarehouseType() == WarehouseVo.LOCAL) {
            orderPaidByPaymentId(id, session.getCustomerId(), warehouseVo.getWarehouseCode());
        }
        //更新客户每日支付信息
        CustomerOrderDailyCountUpdateRequest customerOrderDailyCountUpdateRequest = new CustomerOrderDailyCountUpdateRequest();
        customerOrderDailyCountUpdateRequest.setCustomerId(session.getCustomerId());
        customerOrderDailyCountUpdateRequest.setOrderType(TransactionConstant.OrderType.STOCK_ORDER.getCode());
        customerOrderDailyCountUpdateRequest.setPaymentId(paymentId);
        customerOrderDailyCountUpdateRequest.setPayTime(payTime);
        orderDailyPayCountService.updateCustomerOrderDailyCount(customerOrderDailyCountUpdateRequest);
        //保存交易流水
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(paymentId);
        detail.setUserId(session.getId());
        detail.setCustomerId(session.getCustomerId());
        detail.setPayMethod(PayOrderMethod.RECHARGE);
        detail.setPayAmount(amount);
        detail.setOrderType(OrderType.STOCK);
        detail.setPayTime(payTime);
        sendSavePaymentDetailMessage(detail);
        return detail;
    }


    public synchronized boolean orderPaidByPaymentId(Long orderId, Long customerId, String warehouseCode) {

        StockOrderVo stockOrderVo = stockOrderDao.selectOrderById(orderId);
        List<CustomerStockRecord> records = new ArrayList<>();

        Date date = new Date();

        List<StockOrderItemVo> itemVos = stockOrderVo.getItems();
        itemVos.forEach(stockOrderItem -> {
            CustomerStockRecord record = new CustomerStockRecord();
            record.setCustomerId(customerId);
            record.setOrderType(OrderType.STOCK);
            record.setProductId(stockOrderItem.getProductId());
            record.setQuantity(stockOrderItem.getQuantity());
            record.setRelateId(stockOrderItem.getId());
            record.setVariantId(stockOrderItem.getVariantId());
            record.setVariantImage(stockOrderItem.getVariantImage());
            record.setType(0);
            record.setWarehouseCode(stockOrderVo.getWarehouseCode());
            record.setCreateTime(date);
            record.setUpdateTime(date);
            record.setVariantSku(stockOrderItem.getVariantSku());
            record.setVariantName(stockOrderItem.getVariantName());
            record.setRevokeState(0);
            records.add(record);
        });
        customerStockRecordDao.insertByBatch(records);

        List<StockOrderItem> variantQuantities = stockOrderItemDao.countVariantQuantityByOrderId(orderId);

        List<Long> variantIds = customerProductStockDao.selectWarehouseVariantIdsByCustomer(customerId, warehouseCode);

        List<CustomerProductStock> insertStock = new ArrayList<>();

        List<CustomerProductStock> updateStock = new ArrayList<>();

        variantQuantities.forEach(variantQuantity -> {
            CustomerProductStock stock = new CustomerProductStock();
            stock.setWarehouseCode(warehouseCode);
            if (variantIds.contains(variantQuantity.getVariantId())) {
                stock.setCustomerId(customerId);
                stock.setVariantId(variantQuantity.getVariantId());
                stock.setVariantImage(variantQuantity.getVariantImage());
                stock.setVariantName(variantQuantity.getVariantName());
                stock.setStock(variantQuantity.getQuantity());
                updateStock.add(stock);
            } else {
                stock.setProductTitle(variantQuantity.getProductTitle());
                stock.setVariantSku(variantQuantity.getVariantSku());
                stock.setVariantId(variantQuantity.getVariantId());
                stock.setProductId(variantQuantity.getProductId());
                stock.setVariantImage(variantQuantity.getVariantImage());
                stock.setVariantName(variantQuantity.getVariantName());
                stock.setCustomerId(customerId);
                stock.setStock(variantQuantity.getQuantity());
                stock.setLockStock(0);
                stock.setCreateTime(date);
                stock.setUpdateTime(date);
                stock.setCustomerShowState(1);
                insertStock.add(stock);
            }
        });

        if (ListUtils.isNotEmpty(insertStock)) {
            customerProductStockDao.insertByBatch(insertStock);
        }

        if (ListUtils.isNotEmpty(updateStock)) {
            customerProductStockDao.increaseVariantStock(updateStock);
        }
        return true;
    }

    /**
     *
     */
    @Override
    public StockOrder selectByPrimaryKey(Long id) {
        return stockOrderDao.selectByPrimaryKey(id);
    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(StockOrder record) {
        return stockOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(StockOrder record) {
        return stockOrderDao.updateByPrimaryKey(record);
    }

    /**
     *
     */

    public List<StockOrder> select(Page<StockOrder> record) {
        record.initFromNum();
        return stockOrderDao.select(record);
    }

    /**
     *
     */

    public long count(Page<StockOrder> record) {
        return stockOrderDao.count(record);
    }

    @Override
    public void sendSavePaymentDetailMessage(PaymentDetail detail) {
        Long paymentId = detail.getPaymentId();
        List<TransactionDetail> transactionDetails = stockOrderDao.selectTransactionDetailByPaymentId(paymentId);
        if (ListUtils.isEmpty(transactionDetails)) {
            return;
        }
        detail.setOrderTransactions(transactionDetails);
        Message message = new Message(RocketMqConfig.TOPIC_SAVE_ORDER_TRANSACTION, "stock_order", "stock:order:transaction:" + paymentId, JSON.toJSONBytes(detail));
        message.setDelayTimeLevel(1);
        MqMessageLog messageLog = MqMessageLog.toMqMessageLog(message, detail.toString());
        log.warn("交易ID：{},消息：{}", paymentId, detail);
        String status = "failed";
        int i = 1;
        while (i < 4 && !status.equals(SendStatus.SEND_OK.name())) {
            try {
                status = defaultMQProducer.send(message).getSendStatus().name();
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("payment Id:{},交易信息发送失败,失败次数:{}", paymentId, i);
            } finally {
                i += 1;
            }
        }
        if (status.equals(SendStatus.SEND_OK.name())) {
            messageLog.setIsSendSuccess(1);
            log.warn("payment Id:{},交易信息发送成功", paymentId);
        } else {
            messageLog.setIsSendSuccess(0);
            log.warn("payment Id:{},交易信息发送失败", paymentId);
        }
        umsFeignClient.saveMqLog(messageLog);
    }

    /**
     * 根据paymentId 查询支付号下的所有订单
     *
     * @param paymentId
     * @return
     */
    @Override
    public List<StockOrder> selectStockOrderByPaymentId(Long paymentId) {

        return stockOrderDao.selectStockOrderByPaymentId(paymentId);
    }

}