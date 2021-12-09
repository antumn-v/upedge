package com.upedge.oms.modules.wholesale.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.PayOrderMethod;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.old.tms.ShippingUnit;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.ship.dto.ShipMethodSelectDto;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.async.OrderAsyncTask;
import com.upedge.oms.modules.common.service.MqOnSaiheService;
import com.upedge.oms.modules.common.vo.OrderPayCheckResultVo;
import com.upedge.oms.modules.order.dto.OrderTransactionDto;
import com.upedge.oms.modules.order.vo.ItemDischargeQuantityVo;
import com.upedge.oms.modules.order.vo.OrderProductAmountVo;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.orderShippingUnit.vo.OrderShippingUnitVo;
import com.upedge.oms.modules.sales.service.CustomerProductSalesLogService;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.stock.service.CustomerStockRecordService;
import com.upedge.oms.modules.vat.service.VatRuleService;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderPayService;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderAppVo;
import com.upedge.oms.modules.wholesale.vo.WholesaleOrderItemVo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class WholesaleOrderPayServiceImpl implements WholesaleOrderPayService {

    @Autowired
    WholesaleOrderDao wholesaleOrderDao;

    @Autowired
    WholesaleOrderItemDao wholesaleOrderItemDao;

    @Autowired
    WholesaleOrderService wholesaleOrderService;

    @Autowired
    CustomerProductStockService customerProductStockService;

    @Autowired
    CustomerProductSalesLogService customerProductSalesLogService;

    @Autowired
    CustomerStockRecordService customerStockRecordService;

    @Autowired
    WholesaleOrderServiceImpl wholesaleOrderServiceImpl;

    @Autowired
    VatRuleService vatRuleService;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    TmsFeignClient tmsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OrderAsyncTask orderAsyncTask;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private MqOnSaiheService mqOnSaiheService;

    /**
     * 冗余订单物流单元信息
     */
    @Autowired
    private OrderShippingUnitService orderShippingUnitService;


    @Override
    public OrderPayCheckResultVo orderPayCheck(Long paymentId, BigDecimal amount, List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId, String payType) {
        boolean a = customerProductStockService.orderItemStockCheck(dischargeQuantityVos, customerId);
        if (!a) {
            return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "stock error");
        }
//        wholesaleOrderDao.updateProductAmountByPaymentId(paymentId);

        List<OrderProductAmountVo> orderProductAmountVos = wholesaleOrderItemDao.selectOrderItemAmountByPaymentId(paymentId);
        Map<Long,OrderProductAmountVo> orderProductAmountVoMap = new HashMap<>();
        orderProductAmountVos.forEach(orderProductAmountVo -> {
            orderProductAmountVoMap.put(orderProductAmountVo.getId(),orderProductAmountVo);
        });

        List<WholesaleOrderAppVo> orders = wholesaleOrderDao.selectPayListByPaymentId(paymentId);

        // 检查ship unit
//        a = checkOrderShipUnit(orders);
//        if (!a) {
//            return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "ship error");
//        }

//        a = orderShipPriceCheck(orders);
//        if (!a) {
//            return "ship error";
//        }
        List<Long> orderShipUnitIds = orderShippingUnitService.selectOrderIdByOrderPaymentId(paymentId, OrderType.WHOLESALE);
        for (WholesaleOrderAppVo order : orders) {
            if (!orderShipUnitIds.contains(order.getId())) {
                return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "ship error");
            }
            OrderProductAmountVo orderProductAmountVo = orderProductAmountVoMap.get(order.getId());
            if (null == orderProductAmountVo){
                return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "product error");
            }
            if (order.getProductAmount().compareTo(orderProductAmountVo.getProductAmount()) != 0){
                order.setProductAmount(orderProductAmountVo.getProductAmount());
                wholesaleOrderDao.updateOrderProductAmount(orderProductAmountVo.getId(),orderProductAmountVo.getProductAmount(),orderProductAmountVo.getCnyProductAmount());
                return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "product error");
            }
            BigDecimal vatAmount = vatRuleService.getOrderVatAmount(order.getProductAmount(), order.getShipPrice(), order.getToAreaId(),order.getCustomerId());
            if (vatAmount.compareTo(order.getVatAmount()) != 0) {
                return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "vat error");
            }
        }

        BigDecimal payAmount = wholesaleOrderDao.selectPayAmountByPaymentId(paymentId);
        if (0 != amount.compareTo(payAmount)) {
            return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "amount error");
        }
        int i = wholesaleOrderDao.updatePayStateByPaymentId(paymentId, 2);
        if (i == orders.size()) {
            if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
                customerProductStockService.increaseCustomerLockStock(customerId, dischargeQuantityVos);
            }
            if (StringUtils.equals("paypal", payType)) {
                return creatOrderPayCheckResultVo(orders, paymentId, "success");
            } else {
                return creatOrderPayCheckResultVo(orders, paymentId, "success");
            }
        }
        return creatOrderPayCheckResultVo(new ArrayList<>(), paymentId, "order error");
    }

    private OrderPayCheckResultVo creatOrderPayCheckResultVo(List<WholesaleOrderAppVo> orders, Long paymentId, String str) {
        OrderPayCheckResultVo orderPayCheckResultVo = new OrderPayCheckResultVo();
        orderPayCheckResultVo.setPayMessage(str);
        if (CollectionUtils.isEmpty(orders)) {
            return orderPayCheckResultVo;
        }
        orderPayCheckResultVo.getTradingDataVo().setTransactionId(paymentId.toString());
        Session session = UserUtil.getSession(redisTemplate);
        if (session != null) {
            orderPayCheckResultVo.getTradingDataVo().setTransactionAffiliation(session.getLoginname());
        }
        for (WholesaleOrderAppVo order : orders) {
            orderPayCheckResultVo.getTradingDataVo().setTransactionTotal(
                    orderPayCheckResultVo.getTradingDataVo().getTransactionTotal()
                            .add(order.getProductAmount())
                            .add(order.getShipPrice()
                                    .add(order.getVatAmount())
                            )
            );
            orderPayCheckResultVo.getTradingDataVo().setTransactionShipping(
                    orderPayCheckResultVo.getTradingDataVo().getTransactionShipping()
                            .add(order.getShipPrice())
            );
            if (!CollectionUtils.isEmpty(order.getItemVos())) {
                for (WholesaleOrderItemVo itemVo : order.getItemVos()) {
                    OrderPayCheckResultVo.TransactionProduct transactionProduct = new OrderPayCheckResultVo.TransactionProduct();
                    transactionProduct.setName(itemVo.getAdminVariantName());
                    transactionProduct.setSku(itemVo.getAdminVariantSku());
                    transactionProduct.setPrice(itemVo.getUsdPrice());
                    transactionProduct.setQuantity(itemVo.getQuantity());
                    orderPayCheckResultVo.getTradingDataVo().getTransactionProducts().add(transactionProduct);
                }
            }
        }
        return orderPayCheckResultVo;
    }

    // 支付时判断是否有运输单元冗余信息
    /*boolean checkOrderShipUnit(List<WholesaleOrderAppVo> orders) {
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            final int c = i;
            CompletableFuture<Boolean> orderShipVerify = CompletableFuture.supplyAsync(() -> {
            return   orderShippingUnitService.selectByOrderId(orders.get(c).getId(),OrderType.WHOLESALE) == null ? false:true;
            }, threadPoolExecutor);
            completableFutures.add(orderShipVerify);
        }
        CompletableFuture<String>[] futureArray = completableFutures.toArray(new CompletableFuture[completableFutures.size()]);
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureArray);

        try {
            combinedFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        List<String> combinedResult = Stream.of(futureArray).map(CompletableFuture::join)
                .distinct().collect(Collectors.toList());
       if ( combinedResult.contains(false)){
           return false;
       }
        return  true;
    }*/

    private boolean checkOrderShipUnit(List<WholesaleOrderAppVo> orders) {
        List<Boolean> resultList = Collections.synchronizedList(new ArrayList<Boolean>());
        CountDownLatch latch = new CountDownLatch(orders.size());
        //   ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<Future<Boolean>> futureTaskList = new ArrayList<Future<Boolean>>();
        for (int i = 0; i < orders.size(); i++) {
            int finalI = i;
            futureTaskList.add(threadPoolExecutor.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    try {
                        OrderShippingUnitVo orderShippingUnitVo = orderShippingUnitService.selectByOrderId(orders.get(finalI).getId(), OrderType.WHOLESALE);
                        if (null == orderShippingUnitVo) {
                            resultList.add(false);
                            return false;
                        }
                        resultList.add(true);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    } finally {
                        latch.countDown();
                    }
                }
            }));
        }
        try {
            latch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
            return false;
        }
        System.err.println("resultList:" + resultList.toString());
        if (resultList.contains(false) || resultList.contains("false")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean orderPayRollBack(Long paymentId, Long customerId, List<ItemDischargeQuantityVo> dischargeQuantityVos) {
        log.debug("交易ID：{}，订单支付回滚,库存：{}", paymentId, dischargeQuantityVos);
        String key = RedisKey.STRING_ORDER_PAY + paymentId;

        boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 1000 * 10L);
        if (!flag) {
            return false;
        }

        int i = wholesaleOrderDao.updatePayStateByPaymentId(paymentId, 0);
        List<WholesaleOrderAppVo> wholesaleOrderAppVos = wholesaleOrderDao.selectPayListByPaymentId(paymentId);
        wholesaleOrderAppVos.forEach(e -> {
            orderShippingUnitService.delByOrderId(e.getId(), OrderType.WHOLESALE);
        });
        if (i > 0) {
            if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
                customerProductStockService.increaseFromLockStock(customerId, dischargeQuantityVos);
            }
            RedisUtil.unLock(redisTemplate, key);
            return true;
        }
        RedisUtil.unLock(redisTemplate, key);
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<WholesaleOrderAppVo> orderPayList(List<Long> ids, Session session) {

        Long paymentId = IdGenerate.nextId();

        int i = wholesaleOrderDao.updatePaymentIdByIds(paymentId, ids);
        if (i == 0) {
            return null;
        }

        List<WholesaleOrderAppVo> appVos = wholesaleOrderDao.selectPayListByPaymentId(paymentId);

        refreshOrderDischargeAmount(session.getCustomerId(),appVos);

        List<OrderProductAmountVo> orderProductAmountVos = wholesaleOrderItemDao.selectOrderItemAmountByPaymentId(paymentId);
        Map<Long,OrderProductAmountVo> orderProductAmountVoMap = new HashMap<>();
        orderProductAmountVos.forEach(orderProductAmountVo -> {
            orderProductAmountVoMap.put(orderProductAmountVo.getId(),orderProductAmountVo);
        });

        Map<Long, BigDecimal> vatAmountMap = new HashMap<>();

        List<Long> orderShipUnitIds = orderShippingUnitService.selectOrderIdByOrderPaymentId(paymentId, OrderType.WHOLESALE);
        List<WholesaleOrderAppVo> cantShipOrders = new ArrayList<>();
        for (WholesaleOrderAppVo orderVo : appVos) {
            OrderProductAmountVo orderProductAmountVo = orderProductAmountVoMap.get(orderVo.getId());
            if (null == orderProductAmountVo){
                cantShipOrders.add(orderVo);
                continue;
            }
            if (orderVo.getProductAmount().compareTo(orderProductAmountVo.getProductAmount()) != 0){
                orderVo.setProductAmount(orderProductAmountVo.getProductAmount());
                wholesaleOrderDao.updateOrderProductAmount(orderProductAmountVo.getId(),orderProductAmountVo.getProductAmount(),orderProductAmountVo.getCnyProductAmount());
            }
            if (!orderShipUnitIds.contains(orderVo.getId())) {
                ShipDetail shipDetail = wholesaleOrderService.orderInitShipDetail(orderVo.getId());
                if (shipDetail == null) {
                    cantShipOrders.add(orderVo);
                    continue;
                }
                orderVo.setShipName(shipDetail.getMethodName());
                orderVo.setShipMethodId(shipDetail.getMethodId());
                orderVo.setShipPrice(shipDetail.getPrice());
                orderVo.setTotalWeight(shipDetail.getWeight());
            }
            BigDecimal vatAmount = vatRuleService.getOrderVatAmount(orderVo.getProductAmount(), orderVo.getShipPrice(), orderVo.getToAreaId(),orderVo.getCustomerId());

            if (null == orderVo.getVatAmount() || vatAmount.compareTo(orderVo.getVatAmount()) != 0) {
                orderVo.setVatAmount(vatAmount);
                vatAmountMap.put(orderVo.getId(), vatAmount);
            }
        }
        appVos.removeAll(cantShipOrders);
        if (MapUtils.isNotEmpty(vatAmountMap)) {
            wholesaleOrderDao.updateVatAmountByMap(vatAmountMap);
        }
        return appVos;
    }

    boolean refreshOrderDischargeAmount(Long customerId, List<WholesaleOrderAppVo> appVos){
        CustomerProductStock customerProductStock = new CustomerProductStock();
        customerProductStock.setCustomerId(customerId);
        Page<CustomerProductStock> page = new Page<>();
        page.setT(customerProductStock);
        page.setCondition("stock > 0");
        page.setPageSize(-1);
        List<CustomerProductStock> productStocks = customerProductStockService.select(page);

        if (ListUtils.isNotEmpty(productStocks)) {
            //客户库存
            Map<Long, Integer> map = new HashMap<>();
            for (CustomerProductStock productStock : productStocks) {
                map.put(productStock.getVariantId(), productStock.getStock());
            }
            //需修改订单抵扣费用的集合
            Map<Long, BigDecimal> orderDischargeMap = new HashMap<>();
            //需修改订单产品抵扣数量的集合
            Map<Long, Integer> itemDischargeMap = new HashMap<>();
            appVos.forEach(appOrderVo -> {
                BigDecimal dischargeAmount = BigDecimal.ZERO;
                List<WholesaleOrderItemVo> itemVos = appOrderVo.getItemVos();
                for (WholesaleOrderItemVo appOrderItemVo : itemVos) {
                    Integer dischargeQuantity = 0;
                    if (map.containsKey(appOrderItemVo.getAdminVariantId())) {
                        Integer stock = map.get(appOrderItemVo.getAdminVariantId());
                        if (stock > 0) {
                            Integer quantity = appOrderItemVo.getQuantity();
                            if (stock >= quantity) {
                                dischargeQuantity = quantity;
                                stock = stock - quantity;
                            } else {
                                dischargeQuantity = stock;
                                stock = 0;
                            }
                            if (stock == 0) {
                                map.remove(appOrderItemVo.getAdminVariantId());
                            } else {
                                map.put(appOrderItemVo.getAdminVariantId(), stock);
                            }
                        }
                    }
                    //    if (!dischargeQuantity.equals(appOrderItemVo.getDischargeQuantity())) {
                    if (!dischargeQuantity.equals(0)) {
                        dischargeAmount = dischargeAmount.add(new BigDecimal(dischargeQuantity).multiply(appOrderItemVo.getUsdPrice()));
                        appOrderItemVo.setDischargeQuantity(dischargeQuantity);
                        itemDischargeMap.put(appOrderItemVo.getId(), dischargeQuantity);
                    }
                }
                if (appOrderVo.getProductDischargeAmount() == null ||
                        dischargeAmount.compareTo(appOrderVo.getProductDischargeAmount()) != 0) {
                    orderDischargeMap.put(appOrderVo.getId(), dischargeAmount);
                }
                appOrderVo.setProductDischargeAmount(dischargeAmount);
            });
            if (0 < orderDischargeMap.size()) {
                int j = wholesaleOrderDao.updateDischargeAmountByMap(orderDischargeMap);
                if (j != orderDischargeMap.size()) {
                    return false;
                }
            }
            if (0 < itemDischargeMap.size()) {
                wholesaleOrderItemDao.updateDischargeQuantityByMap(itemDischargeMap);
            }
        }
        return true;
    }

    private void MateShippingUnit(Map<Long, ShipDetail> orderShipMap) {
        orderShipMap.forEach((k, v) ->
        {
            if (v.getShippingUtilId() != null) {
                // 删除原来的unit  并插入新的冗余
                //OrderShippingUnitVo OrderShippingUnitVo = orderShippingUnitService.selectByOrderId(id,OrderType.WHOLESALE);
                orderShippingUnitService.delByOrderId(k, OrderType.WHOLESALE);
                BaseResponse shippingUnitResponse = tmsFeignClient.unitInfo(v.getShippingUtilId());
                if (shippingUnitResponse.getCode() == ResultCode.SUCCESS_CODE && shippingUnitResponse.getData() != null) {
                    ShippingUnit shippingUnit = JSON.parseObject(JSON.toJSONString(shippingUnitResponse.getData()), ShippingUnit.class);
                    OrderShippingUnit orderShippingUnit = new OrderShippingUnit();
                    BeanUtils.copyProperties(shippingUnit, orderShippingUnit);
                    orderShippingUnit.setOrderId(k);
                    orderShippingUnit.setOrderType(OrderType.WHOLESALE);
                    orderShippingUnit.setId(IdGenerate.nextId());
                    orderShippingUnit.setShipUnitId(shippingUnit.getId());
                    orderShippingUnit.setCreateTime(new Date());
                    orderShippingUnitService.insert(orderShippingUnit);
                }
            }
        });
    }


    @GlobalTransactional
    @Override
    public String payOrderByBalance(Long paymentId, BigDecimal amount, Session session, Date payTime) {
        AccountPaymentRequest paymentRequest = new AccountPaymentRequest();
        paymentRequest.setAccountId(session.getAccountId());
        paymentRequest.setCustomerId(session.getCustomerId());
        paymentRequest.setFixFee(BigDecimal.ZERO);
        paymentRequest.setOrderType(OrderType.WHOLESALE);
        paymentRequest.setPayType(PayOrderMethod.RECHARGE);
        paymentRequest.setUserId(session.getId());
        paymentRequest.setId(paymentId);
        paymentRequest.setPayAmount(amount);

        OrderTransactionDto orderTransactionDto = new OrderTransactionDto();
        orderTransactionDto.setFixFeePercentage(BigDecimal.ZERO);
        orderTransactionDto.setPaymentId(paymentId);
        orderTransactionDto.setPayTime(payTime);
        orderTransactionDto.setPayMethod(PayOrderMethod.RECHARGE);

        BigDecimal cnyRate = (BigDecimal) redisTemplate.opsForHash().get("currency:rate:USD", "cnyRate");
        if (null == cnyRate) {
            cnyRate = new BigDecimal((Double) umsFeignClient.getCurrencyRate("USD").getData());
        }
        orderTransactionDto.setCnyRate(cnyRate);

        BaseResponse response = umsFeignClient.accountPayment(paymentRequest);
        if (response.getCode() != ResultCode.SUCCESS_CODE) {
            return response.getMsg();
        }
        wholesaleOrderDao.updateOrderAsPaid(orderTransactionDto);

        return "success";
    }

    @Transactional
    @Override
    public BaseResponse createPaypalOrder(Session session, BigDecimal amount, Long paymentId) {

        List<WholesaleOrderAppVo> appVos = wholesaleOrderDao.selectPayListByPaymentId(paymentId);
        PaypalOrder paypalOrder = new PaypalOrder();
        paypalOrder.setId(paymentId);
        paypalOrder.setAccountId(session.getAccountId());
        paypalOrder.setOrderType(OrderType.WHOLESALE);
        paypalOrder.setSession(session);
        HttpServletRequest request = RequestUtil.getRequest();
        String origin = request.getHeader("Origin");
        paypalOrder.setSuccessUrl(origin + "/payment/wholesalePayPal");
        paypalOrder.setFailedUrl(origin + "/payment/wholesalePayPal");
        paypalOrder.setAccountPaymethodId(PayOrderMethod.PAYPAL);
        paypalOrder.setAmount(amount);
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal shipPrice = BigDecimal.ZERO;
        List<PaypalOrder.PaypalOrderItem> orderItems = new ArrayList<>();
        for (WholesaleOrderAppVo orderVo : appVos) {
            productAmount = productAmount.add(orderVo.getProductAmount());
            shipPrice = shipPrice.add(orderVo.getShipPrice().add(orderVo.getVatAmount()));
            List<WholesaleOrderItemVo> orderItemVos = orderVo.getItemVos();
            for (WholesaleOrderItemVo itemVo : orderItemVos) {
                PaypalOrder.PaypalOrderItem orderItem = new PaypalOrder.PaypalOrderItem();
                orderItem.setItemId(itemVo.getId());
                orderItem.setOrderId(itemVo.getOrderId());
                orderItem.setPrice(itemVo.getUsdPrice());
                orderItem.setQuantity(itemVo.getQuantity());
                orderItem.setSku(itemVo.getAdminVariantSku());
                orderItems.add(orderItem);
            }
        }
        paypalOrder.setShipPrice(shipPrice);
        paypalOrder.setProductAmount(productAmount);
        paypalOrder.setItems(orderItems);
        if (!CollectionUtils.isEmpty(appVos)) {
            paypalOrder.setStoreName(appVos.get(0).getStoreName());
        }
        BaseResponse response = umsFeignClient.getPaypalOrderUrl(paypalOrder);
        return response;
    }

    @Transactional
    @Override
    public PaymentDetail payOrderByPaypal(Long userId, Long customerId, Long paymentId) {
        Date payTime = new Date();
        BigDecimal amount = wholesaleOrderDao.selectPayAmountByPaymentId(paymentId);

        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(paymentId);
        detail.setUserId(userId);
        detail.setCustomerId(customerId);
        detail.setPayMethod(PayOrderMethod.PAYPAL);
        detail.setPayAmount(amount);
        detail.setOrderType(OrderType.WHOLESALE);
        detail.setPayTime(payTime);

        OrderTransactionDto orderTransactionDto = new OrderTransactionDto();
        orderTransactionDto.setFixFeePercentage(Constant.PAYPAL_FEE_PERCENTAGE);
        orderTransactionDto.setPaymentId(paymentId);
        orderTransactionDto.setPayTime(new Date());
        orderTransactionDto.setPayMethod(PayOrderMethod.RECHARGE);
        BigDecimal cnyRate = (BigDecimal) redisTemplate.opsForHash().get("currency:rate:USD", "cnyRate");
        if (null == cnyRate) {
            cnyRate = new BigDecimal((Double) umsFeignClient.getCurrencyRate("USD").getData());
        }
        orderTransactionDto.setCnyRate(cnyRate);

        wholesaleOrderDao.updateOrderAsPaid(orderTransactionDto);
        List<ItemDischargeQuantityVo> dischargeQuantityVos = wholesaleOrderItemDao.selectDischargeQuantityByPaymentId(paymentId);
        if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
            customerProductStockService.reduceFromLockStock(customerId, dischargeQuantityVos);
        }
        return detail;
    }


    @Override
    public void processAfterPaid(Long paymentId, Long customerId, Long userId, Date payTime) {

        Future<?> submit = threadPoolExecutor.submit(() -> {

            customerProductSalesLogService.saveProductSaleRecord(paymentId, OrderType.WHOLESALE, customerId, new Date());
            customerStockRecordService.saveDischargeStockRecordByPaymentId(customerId, paymentId, OrderType.WHOLESALE);
            sendSaveTransactionRecordMessage(paymentId, customerId, userId, TransactionConstant.PayMethod.ACCOUNT.getCode());
            // 发送信息到mq   mq消费 上传赛盒  放在 sendSaveTransactionRecordMessage消费端
            //    sendWholesaleOrderUploadSaiheMessage(paymentId);
        });
        try {
            submit.get();
        } catch (Exception e) {
            log.error("paymentId:{},customerId:{},userId:{},payTime:{}", paymentId, customerId, userId, payTime);
            e.printStackTrace();
        }
    }

    /**
     * 将订单推送到赛盒
     *
     * @param paymentId
     */
    private void sendWholesaleOrderUploadSaiheMessage(Long paymentId) {
        mqOnSaiheService.uploadPaymentIdOnMq(paymentId, OrderType.WHOLESALE);
    }

    @Override
    public int updatePayStateByPaymentId(Long paymentId, Integer payState) {
        return wholesaleOrderDao.updatePayStateByPaymentId(paymentId, payState);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePaymentIdByIds(Long paymentId, List<Long> ids) {
        return wholesaleOrderDao.updatePaymentIdByIds(paymentId, ids);
    }

    /**
     * 订单运输信息检查
     *
     * @param appVos
     * @return
     */
    boolean orderShipPriceCheck(List<WholesaleOrderAppVo> appVos) {
        Map<String, BigDecimal> map = new HashMap<>();
        List<ShipMethodBatchSearchRequest.BatchShipMethodSelectDto> batchShipMethodSelectDtos = new ArrayList<>();
        for (WholesaleOrderAppVo order : appVos) {
            List<WholesaleOrderItemVo> itemVos = order.getItemVos();
            ShipMethodBatchSearchRequest.BatchShipMethodSelectDto methodSelectDto = new ShipMethodBatchSearchRequest.BatchShipMethodSelectDto();
            BigDecimal weight = BigDecimal.ZERO;
            BigDecimal volume = BigDecimal.ZERO;
            for (WholesaleOrderItemVo itemVo : itemVos) {
                BigDecimal quantity = new BigDecimal(itemVo.getQuantity());
                weight = weight.add(quantity.multiply(itemVo.getAdminVariantWeight()));
                volume = volume.add(quantity.multiply(itemVo.getAdminVariantVolume()));
            }
            Set<Long> set = new HashSet<>();
            set.add(order.getShipMethodId());
            ShipMethodSelectDto shipMethodSelectDto = new ShipMethodSelectDto();
            shipMethodSelectDto.setMethodIds(set);
            shipMethodSelectDto.setToAreaId(order.getToAreaId());
            shipMethodSelectDto.setVolumeWeight(volume);
            shipMethodSelectDto.setWeight(weight);
            methodSelectDto.setShipMethodSelectDto(shipMethodSelectDto);
            String key = UUID.randomUUID().toString();
            methodSelectDto.setRequestId(key);
            batchShipMethodSelectDtos.add(methodSelectDto);
            map.put(key, order.getShipPrice());
        }
        ShipMethodBatchSearchRequest request = new ShipMethodBatchSearchRequest();
        request.setBatchShipMethodSelectDtos(batchShipMethodSelectDtos);
        ShipMethodBatchSearchResponse response = tmsFeignClient.batchShipSearch(request);
        if (ResultCode.FAIL_CODE == response.getCode() || ListUtils.isEmpty(response.getBatchShipMethodSelectVos())) {
            return false;
        }
        List<ShipMethodBatchSearchResponse.BatchShipMethodSelectVo> batchShipMethodSelectVos = response.getBatchShipMethodSelectVos();
        for (ShipMethodBatchSearchResponse.BatchShipMethodSelectVo batchShipMethodSelectVo : batchShipMethodSelectVos) {
            BigDecimal price = batchShipMethodSelectVo.getShips().get(0).getPrice();
            BigDecimal orderShipPrice = map.get(batchShipMethodSelectVo.getRequestId());
            if (price.compareTo(orderShipPrice) != 0) {
                return false;
            }
            map.remove(batchShipMethodSelectVo.getRequestId());
        }
        if (map.size() != 0) {
            return false;
        }

        return true;
    }

    /**
     * 发送保存交易信息的消息
     *
     * @param paymentId
     * @param customerId
     * @param userId
     * @param payMethod
     */
    public void sendSaveTransactionRecordMessage(Long paymentId, Long customerId, Long userId, Integer payMethod) {
        BigDecimal payAmount = wholesaleOrderDao.selectPayAmountByPaymentId(paymentId);
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(paymentId);
        detail.setUserId(userId);
        detail.setCustomerId(customerId);
        detail.setPayMethod(payMethod);
        detail.setPayAmount(payAmount);
        detail.setOrderType(OrderType.WHOLESALE);
        detail.setPayTime(new Date());

        List<TransactionDetail> transactionDetails = wholesaleOrderDao.selectTransactionDetailByPaymentId(paymentId);
        if (ListUtils.isEmpty(transactionDetails)) {
            return;
        }

        detail.setOrderTransactions(transactionDetails);

        Message message = new Message("order_transaction", "wholesale_order", "wholesale:order:transaction:" + paymentId, JSON.toJSONBytes(detail));
        message.setDelayTimeLevel(1);

        umsFeignClient.sendMessage(message);

    }
}
