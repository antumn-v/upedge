package com.upedge.oms.modules.order.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.*;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.enums.TransactionConstant;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalOrder.PaypalOrderItem;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.order.request.CustomerOrderDailyCountUpdateRequest;
import com.upedge.common.model.ship.dto.ShipMethodSelectDto;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest;
import com.upedge.common.model.ship.request.ShipMethodBatchSearchRequest.BatchShipMethodSelectDto;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse;
import com.upedge.common.model.ship.response.ShipMethodBatchSearchResponse.BatchShipMethodSelectVo;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.oms.async.OrderAsyncTask;
import com.upedge.oms.modules.common.service.MqOnSaiheService;
import com.upedge.oms.modules.common.vo.OrderPayCheckResultVo;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderItemDao;
import com.upedge.oms.modules.order.dto.AppOrderListDto;
import com.upedge.oms.modules.order.dto.OrderTransactionDto;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.request.AppOrderListRequest;
import com.upedge.oms.modules.order.service.OrderPayService;
import com.upedge.oms.modules.order.service.OrderProfitService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.*;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.orderShippingUnit.vo.OrderShippingUnitVo;
import com.upedge.oms.modules.sales.service.CustomerProductSalesLogService;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import com.upedge.oms.modules.stock.dao.CustomerProductStockDao;
import com.upedge.oms.modules.stock.entity.CustomerProductStock;
import com.upedge.oms.modules.stock.service.CustomerProductStockService;
import com.upedge.oms.modules.stock.service.CustomerStockRecordService;
import com.upedge.oms.modules.stock.vo.CustomerWarehouseVariantStockVo;
import com.upedge.oms.modules.vat.service.VatRuleService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@Service
public class OrderPayServiceImpl implements OrderPayService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    CustomerProductStockDao customerProductStockDao;

    @Autowired
    OrderService orderService;

    @Autowired
    VatRuleService vatRuleService;

    @Autowired
    CustomerProductStockService customerProductStockService;

    @Autowired
    CustomerProductSalesLogService customerProductSalesLogService;

    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    TmsFeignClient tmsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OrderAsyncTask orderAsyncTask;

    @Autowired
    CustomerStockRecordService customerStockRecordService;

    @Autowired
    OrderProfitService orderProfitService;

    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Autowired
    OrderDailyPayCountService orderDailyPayCountService;

    @Autowired
    private MqOnSaiheService mqOnSaiheService;

    /**
     * 冗余订单物流单元信息
     */
    @Autowired
    private OrderShippingUnitService orderShippingUnitService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse orderPayList(Long paymentId, Session session, String warehouseCode) throws CustomerException, ExecutionException, InterruptedException {
        AppOrderListRequest appOrderListRequest = new AppOrderListRequest();
        AppOrderListDto appOrderListDto = new AppOrderListDto();
        appOrderListDto.setPaymentId(paymentId);
        appOrderListDto.setPayState(0);
        appOrderListDto.setQuoteState(3);
        appOrderListRequest.setT(appOrderListDto);
        appOrderListRequest.setPageSize(-1);
        Long customerId = session.getCustomerId();
        List<AppOrderVo> orderVos = orderService.selectAppOrderList(appOrderListRequest);
        if (ListUtils.isEmpty(orderVos)) {
            return BaseResponse.failed("The order does not meet the payment conditions");
        }

        refreshOrderStockDischarge(customerId, paymentId);

        List<OrderProductAmountVo> orderProductAmountVos = orderDao.selectOrderItemAmountByPaymentId(paymentId);
        Map<Long, OrderProductAmountVo> orderProductAmountVoMap = new HashMap<>();
        orderProductAmountVos.forEach(orderProductAmountVo -> {
            orderProductAmountVoMap.put(orderProductAmountVo.getId(), orderProductAmountVo);
        });
        List<Long> orderShipUnitIds = orderShippingUnitService.selectOrderIdByOrderPaymentId(paymentId, OrderType.NORMAL);
        List<AppOrderVo> cantShipOrders = new ArrayList<>();
        Map<Long, BigDecimal> vatAmountMap = new ConcurrentHashMap<>();
        for (AppOrderVo orderVo : orderVos) {
            Long shipMethodId = orderVo.getShipMethodId();
            OrderProductAmountVo orderProductAmountVo = orderProductAmountVoMap.get(orderVo.getId());
            if (null == shipMethodId || null == orderProductAmountVo) {
//                return BaseResponse.failed("The order "+orderVo.getId()+" has no shipping method selected");
                cantShipOrders.add(orderVo);
                continue;
            }
            //验证产品费
            if (orderVo.getProductAmount().compareTo(orderProductAmountVo.getProductAmount()) != 0
                    || orderVo.getProductDischargeAmount().compareTo(orderProductAmountVo.getDischargeAmount()) != 0 ) {
                orderVo.setProductAmount(orderProductAmountVo.getProductAmount());
                orderVo.setProductDischargeAmount(orderProductAmountVo.getDischargeAmount());
                orderDao.updateOrderProductAmount(orderProductAmountVo);
            }
            //检查运输方式
            if (StringUtils.isBlank(orderVo.getShippingWarehouse())){
                cantShipOrders.add(orderVo);
                continue;
            }
            WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + orderVo.getShippingWarehouse());
            if (null == warehouseVo){
                cantShipOrders.add(orderVo);
                continue;
            }
            if (orderVo.getShippingWarehouse().equals(ProductConstant.DEFAULT_WAREHOUSE_ID)) {
                if (!orderShipUnitIds.contains(orderVo.getId())) {
                    cantShipOrders.add(orderVo);
                    continue;
                }
            }else {
                boolean b = checkOrderOverseaShipMethod(orderVo);
                if (!b){
                    cantShipOrders.add(orderVo);
                    continue;
                }
            }
            ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD, orderVo.getShipMethodId().toString());
            if (null != shippingMethodRedis) {
                orderVo.setShipMethodName(shippingMethodRedis.getName());
            }
            //检查vat
            BigDecimal vatAmount = vatRuleService.getOrderVatAmount(orderVo.getProductAmount(), orderVo.getShipPrice(), orderVo.getToAreaId(), orderVo.getCustomerId());
            if (null == orderVo.getVatAmount() || vatAmount.compareTo(orderVo.getVatAmount()) != 0) {
                orderVo.setVatAmount(vatAmount);
                vatAmountMap.put(orderVo.getId(), vatAmount);
            }
        }
        orderVos.removeAll(cantShipOrders);
        if (MapUtils.isNotEmpty(vatAmountMap)) {
            orderDao.updateVatAmountByMap(vatAmountMap);
        }
        return BaseResponse.success(orderVos);
    }

//    public void refreshOrderStockDischarge(Long customerId, List<AppOrderVo> orderVos) {
//        CustomerProductStock customerProductStock = new CustomerProductStock();
//        customerProductStock.setCustomerId(customerId);
//        Page<CustomerProductStock> page = new Page<>();
//        page.setT(customerProductStock);
//        page.setCondition("stock > 0");
//        page.setPageSize(-1);
//        List<CustomerProductStock> productStocks = customerProductStockDao.select(page);
//        if (ListUtils.isNotEmpty(productStocks)) {
//            //客户库存
//            Map<Long, Integer> map = new HashMap<>();
//            //需修改订单抵扣费用的集合
//            Map<Long, BigDecimal> orderDischargeMap = new HashMap<>();
//            //需修改订单产品抵扣数量的集合
//            Map<Long, Integer> itemDischargeMap = new HashMap<>();
//            for (CustomerProductStock productStock : productStocks) {
//                map.put(productStock.getVariantId(), productStock.getStock());
//            }
//            orderVos.forEach(appOrderVo -> {
//                BigDecimal dischargeAmount = BigDecimal.ZERO;
//                Set<AppStoreOrderVo> storeOrderVos = appOrderVo.getStoreOrderVos();
//                for (AppStoreOrderVo storeOrderVo : storeOrderVos) {
//                    Set<AppOrderItemVo> itemVos = storeOrderVo.getItemVos();
//                    for (AppOrderItemVo appOrderItemVo : itemVos) {
//                        Integer dischargeQuantity = 0;
//                        if (map.containsKey(appOrderItemVo.getAdminVariantId())) {
//                            Integer stock = map.get(appOrderItemVo.getAdminVariantId());
//                            if (stock > 0) {
//                                Integer quantity = appOrderItemVo.getQuantity();
//                                if (stock >= quantity) {
//                                    dischargeQuantity = quantity;
//                                    stock = stock - quantity;
//                                } else {
//                                    dischargeQuantity = stock;
//                                    stock = 0;
//                                }
//                                if (stock == 0) {
//                                    map.remove(appOrderItemVo.getAdminVariantId());
//                                } else {
//                                    map.put(appOrderItemVo.getAdminVariantId(), stock);
//                                }
//                            }
//                        }
//                        dischargeAmount = dischargeAmount.add(new BigDecimal(dischargeQuantity).multiply(appOrderItemVo.getPrice()));
//                        appOrderItemVo.setDischargeQuantity(dischargeQuantity);
//                        itemDischargeMap.put(appOrderItemVo.getId(), dischargeQuantity);
//                    }
//                }
//                orderDischargeMap.put(appOrderVo.getId(), dischargeAmount);
//                appOrderVo.setProductDischargeAmount(dischargeAmount);
//            });
//            if (0 < orderDischargeMap.size()) {
//                orderDao.updateDischargeAmountByMap(orderDischargeMap);
//            }
//            if (0 < itemDischargeMap.size()) {
//                orderItemDao.updateDischargeQuantityByMap(itemDischargeMap);
//            }
//        }
//    }

    public void refreshOrderStockDischarge(Long customerId, List<AppOrderVo> orderVos, String warehouseCode) throws CustomerException {
        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + warehouseCode);
        if (warehouseVo == null) {
            throw new CustomerException("Warehouse Error！");
        }

        CustomerProductStock customerProductStock = new CustomerProductStock();
        customerProductStock.setCustomerId(customerId);
        customerProductStock.setWarehouseCode(warehouseCode);
        Page<CustomerProductStock> page = new Page<>();
        page.setT(customerProductStock);
        page.setCondition("stock > 0");
        page.setPageSize(-1);
        List<CustomerProductStock> productStocks = customerProductStockDao.select(page);
        if (ListUtils.isNotEmpty(productStocks)) {
            //客户库存
            Map<Long, Integer> map = new HashMap<>();
            //需修改订单抵扣费用的集合
            Map<Long, BigDecimal> orderDischargeMap = new HashMap<>();
            //需修改订单产品抵扣数量的集合
            Map<Long, Integer> itemDischargeMap = new HashMap<>();
            for (CustomerProductStock productStock : productStocks) {
                map.put(productStock.getVariantId(), productStock.getStock());
            }
            boolean b = true;
            for (AppOrderVo appOrderVo : orderVos) {
                BigDecimal dischargeAmount = BigDecimal.ZERO;
                Set<AppStoreOrderVo> storeOrderVos = appOrderVo.getStoreOrderVos();
                for (AppStoreOrderVo storeOrderVo : storeOrderVos) {
                    Set<AppOrderItemVo> itemVos = storeOrderVo.getItemVos();
                    for (AppOrderItemVo appOrderItemVo : itemVos) {
                        Integer dischargeQuantity = 0;
                        if (map.containsKey(appOrderItemVo.getAdminVariantId())) {
                            Integer stock = map.get(appOrderItemVo.getAdminVariantId());
                            if (stock > 0) {
                                Integer quantity = appOrderItemVo.getQuantity();
                                if (stock >= quantity) {
                                    dischargeQuantity = quantity;
                                    stock = stock - quantity;
                                } else {
                                    if (warehouseVo.getWarehouseType() == WarehouseVo.OVERSEAS) {
                                        b = false;
                                        break;
                                    }
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
                        dischargeAmount = dischargeAmount.add(new BigDecimal(dischargeQuantity).multiply(appOrderItemVo.getPrice()));
                        appOrderItemVo.setDischargeQuantity(dischargeQuantity);
                        itemDischargeMap.put(appOrderItemVo.getId(), dischargeQuantity);
                    }
                    if (!b) {
                        break;
                    }
                }
                orderDischargeMap.put(appOrderVo.getId(), dischargeAmount);
                appOrderVo.setProductDischargeAmount(dischargeAmount);
            }
            if (!b) {
                throw new CustomerException("Insufficient stock in overseas warehouses");
            }
            if (0 < orderDischargeMap.size()) {
                orderDao.updateDischargeAmountByMap(orderDischargeMap);
            }
            if (0 < itemDischargeMap.size()) {
                orderItemDao.updateDischargeQuantityByMap(itemDischargeMap);
            }
        } else {
            if (warehouseVo.getWarehouseType() == WarehouseVo.OVERSEAS) {
                throw new CustomerException("Insufficient stock in overseas warehouses");
            }

        }
    }

    public void refreshOrderStockDischarge(Long customerId, Long paymentId) {
        List<CustomerWarehouseVariantStockVo> customerWarehouseVariantStockVos = customerProductStockDao.selectCustomerWarehouseVariantStock(customerId);
        if (ListUtils.isEmpty(customerWarehouseVariantStockVos)){
            return;
        }
        //需修改订单产品抵扣数量的集合
        Map<Long, Integer> itemDischargeMap = new HashMap<>();
        Map<String,Integer> variantWarehouseStockMap = new HashMap<>();
        for (CustomerWarehouseVariantStockVo customerWarehouseVariantStockVo : customerWarehouseVariantStockVos) {
            String key = customerWarehouseVariantStockVo.getVariantId() + customerWarehouseVariantStockVo.getWarehouseCode();
            variantWarehouseStockMap.put(key,customerWarehouseVariantStockVo.getStock());
        }
        List<OrderItem> orderItems = orderItemDao.selectItemByPaymentId(paymentId);
        for (OrderItem orderItem : orderItems) {
            String key = orderItem.getAdminVariantId() + orderItem.getShippingWarehouse();
            Integer stock = variantWarehouseStockMap.get(key);
            if (stock == null
            || stock == 0){
                continue;
            }
            Integer dischargeQuantity = 0;
            Integer quantity = orderItem.getQuantity();
            if (stock >= quantity) {
                dischargeQuantity = quantity;
            }else {
                dischargeQuantity = stock;
                stock = 0;
            }
            stock = stock - dischargeQuantity;
            if (!dischargeQuantity.equals(orderItem.getDischargeQuantity())){
                orderItem.setDischargeQuantity(dischargeQuantity);
                itemDischargeMap.put(orderItem.getId(),dischargeQuantity);
            }
            variantWarehouseStockMap.put(key, stock);
        }
        if (MapUtils.isNotEmpty(itemDischargeMap)){
            orderItemDao.updateDischargeQuantityByMap(itemDischargeMap);
        }
    }

    @Override
    public List<AppOrderVo> selectPayOrderListByPaymentId(Long paymentId) {
        return orderDao.selectPayOrderListByPaymentId(paymentId);
    }

    @Override
    public int updatePaymentIdByIds(List<Long> ids, Long paymentId, Long customerId) {
        return orderDao.updatePaymentIdByIds(paymentId, customerId, ids);
    }

    @Override
    public int updatePayStateByPaymentId(Long paymentId, Integer payState) {
        return orderDao.updatePayStateByPaymentId(paymentId, payState);
    }

    @Override
    public boolean orderPayRollback(Long paymentId, Long customerId, List<ItemDischargeQuantityVo> dischargeQuantityVos) {
        String key = RedisKey.STRING_ORDER_PAY + paymentId;

        boolean flag = RedisUtil.lock(redisTemplate, key, 3L, 1000 * 10L);
        if (!flag) {
            return false;
        }

        int i = orderDao.updatePayStateByPaymentId(paymentId, 0);
        List<Order> orders = orderDao.selectOrderByPaymentId(paymentId);
        orders.forEach(e -> {
            orderService.orderInitShipDetail(e.getId(), ProductConstant.DEFAULT_WAREHOUSE_ID);
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

    /**
     * 检查库存，运费
     *
     * @param paymentId
     * @param amount
     * @param dischargeQuantityVos
     * @param customerId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse orderPayCheck(Long paymentId, BigDecimal amount, List<ItemDischargeQuantityVo> dischargeQuantityVos, Long customerId, String payType) {
        boolean a = customerProductStockService.orderItemStockCheck(dischargeQuantityVos, customerId);
        if (!a) {
            return BaseResponse.failed("stock error");
        }
//        orderDao.updateProductAmountByPaymentId(paymentId);
        AppOrderListRequest appOrderListRequest = new AppOrderListRequest();
        AppOrderListDto appOrderListDto = new AppOrderListDto();
        appOrderListDto.setPaymentId(paymentId);
        appOrderListRequest.setT(appOrderListDto);
        appOrderListRequest.setPageSize(-1);
        List<AppOrderVo> orders = orderService.selectAppOrderList(appOrderListRequest);
        if (ListUtils.isEmpty(orders)) {
            return BaseResponse.failed();
        }
        //检查运输方式
        List<Long> orderIds = orderShippingUnitService.selectOrderIdByOrderPaymentId(paymentId, OrderType.NORMAL);
        for (AppOrderVo order : orders) {
            if (order.getShippingWarehouse().equals(ProductConstant.DEFAULT_WAREHOUSE_ID)){//本地仓
                if (!orderIds.contains(order.getId())) {
                    return BaseResponse.failed("ship error");
                }
            }else {//海外仓
                boolean b = checkOrderOverseaShipMethod(order);
                if (!b){
                    return BaseResponse.failed("Insufficient inventory of overseas warehouse products");
                }
            }
        }
        //检查产品费和vat
        List<OrderProductAmountVo> orderProductAmountVos = orderDao.selectOrderItemAmountByPaymentId(paymentId);
        Map<Long, OrderProductAmountVo> orderProductAmountVoMap = new HashMap<>();
        orderProductAmountVos.forEach(orderProductAmountVo -> {
            orderProductAmountVoMap.put(orderProductAmountVo.getId(), orderProductAmountVo);
        });
        for (AppOrderVo order : orders) {
            OrderProductAmountVo orderProductAmountVo = orderProductAmountVoMap.get(order.getId());
            if (null == orderProductAmountVo){
                return BaseResponse.failed( "product amount error");
            }
            if (orderProductAmountVo.getProductAmount().compareTo(order.getProductAmount()) != 0
                    || orderProductAmountVo.getDischargeAmount().compareTo(order.getProductDischargeAmount()) != 0 ) {
                orderDao.updateOrderProductAmount(orderProductAmountVo);
                return BaseResponse.failed( "product amount error");
            }
            BigDecimal vatAmount = vatRuleService.getOrderVatAmount(order.getProductAmount(), order.getShipPrice(), order.getToAreaId(), order.getCustomerId());
            if (vatAmount.compareTo(order.getVatAmount()) != 0) {
                orderDao.updateOrderVatAmountById(order.getId(), vatAmount);
                return BaseResponse.failed("vat error");
            }
        }
        BigDecimal payAmount = orderDao.selectPayAmountByPaymentId(paymentId);
        if (0 != amount.compareTo(payAmount)) {
            return BaseResponse.failed("amount error");
        }
        int i = orderDao.updatePayStateByPaymentId(paymentId, 2);
        if (i == orders.size()) {
            if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
                customerProductStockService.increaseCustomerLockStock(customerId, dischargeQuantityVos);
            }
            return BaseResponse.success();
        }
        return BaseResponse.failed("order error");
    }

    boolean checkOrderOverseaShipMethod(AppOrderVo appOrderVo){
        Set<AppStoreOrderVo> appStoreOrderVos = appOrderVo.getStoreOrderVos();
        for (AppStoreOrderVo appStoreOrderVo : appStoreOrderVos) {
            Set<AppOrderItemVo> itemVos = appStoreOrderVo.getItemVos();
            for (AppOrderItemVo itemVo : itemVos) {
                boolean b = customerProductStockService.redisCheckCustomerVariantStock(appOrderVo.getCustomerId(),itemVo.getAdminVariantId(),appOrderVo.getShippingWarehouse(),itemVo.getQuantity());
                if (!b){
                    return b;
                }
            }
        }
        return true;
    }



    private OrderPayCheckResultVo creatOrderPayCheckResultVo(List<AppOrderVo> orders, Long paymentId, String str) {
        OrderPayCheckResultVo orderPayCheckResultVo = new OrderPayCheckResultVo();
        orderPayCheckResultVo.setPayMessage(str);
        if (CollectionUtils.isEmpty(orders)) {
            return orderPayCheckResultVo;
        }
        orderPayCheckResultVo.getTradingDataVo().setTransactionId(paymentId.toString());
        orderPayCheckResultVo.getTradingDataVo().setTransactionAffiliation(orders.get(0).getStoreName());
        for (AppOrderVo order : orders) {
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
            if (!CollectionUtils.isEmpty(order.getStoreOrderVos())) {
                for (AppStoreOrderVo storeOrderVo : order.getStoreOrderVos()) {
                    for (AppOrderItemVo itemVo : storeOrderVo.getItemVos()) {
                        OrderPayCheckResultVo.TransactionProduct transactionProduct = new OrderPayCheckResultVo.TransactionProduct();
                        transactionProduct.setName(itemVo.getStoreVariantName());
                        transactionProduct.setSku(itemVo.getStoreVariantSku());
                        transactionProduct.setPrice(itemVo.getPrice());
                        transactionProduct.setQuantity(itemVo.getQuantity());
                        orderPayCheckResultVo.getTradingDataVo().getTransactionProducts().add(transactionProduct);
                    }
                }
            }
        }
        return orderPayCheckResultVo;
    }


    // 支付时判断是否有运输单元冗余信息
/*   private boolean checkOrderShipUnit(List<AppOrderVo> orders) {
        List<CompletableFuture> completableFutures = new ArrayList<>();
        for (int i = 0; i < orders.size(); i++) {
            final int c = i;
            CompletableFuture<Boolean> orderShipVerify = CompletableFuture.supplyAsync(() -> {
                OrderShippingUnitVo orderShippingUnitVo = orderShippingUnitService.selectByOrderId(orders.get(c).getId(), OrderType.NORMAL);
              if (null == orderShippingUnitVo){
                  return false;
              }
              return true;
            }, threadPoolExecutor);
            completableFutures.add(orderShipVerify);
        }
        CompletableFuture<String>[] futureArray = completableFutures.toArray(new CompletableFuture[completableFutures.size()]);
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futureArray);
        combinedFuture.join();
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
    // 支付时判断是否有运输单元冗余信息
    private boolean checkOrderShipUnit(List<AppOrderVo> orders) {
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
                        OrderShippingUnitVo orderShippingUnitVo = orderShippingUnitService.selectByOrderId(orders.get(finalI).getId(), OrderType.NORMAL);
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

    @GlobalTransactional
    //余额支付订单
    @Override
    public String payOrderByBalance(Session session, BigDecimal amount, Long paymentId, List<ItemDischargeQuantityVo> dischargeQuantityVos) {
        Long customerId = session.getCustomerId();
        Date payTime = new Date();
        AccountPaymentRequest request = new AccountPaymentRequest();
        request.setAccountId(session.getAccountId());
        request.setCustomerId(session.getCustomerId());
        request.setFixFee(BigDecimal.ZERO);
        request.setOrderType(OrderType.NORMAL);
        request.setPayType(PayOrderMethod.RECHARGE);
        request.setUserId(session.getId());
        request.setId(paymentId);
        request.setPayAmount(amount);
        OrderTransactionDto orderTransactionDto = new OrderTransactionDto();
        orderTransactionDto.setFixFeePercentage(BigDecimal.ZERO);
        orderTransactionDto.setPaymentId(paymentId);
        orderTransactionDto.setPayTime(payTime);
        orderTransactionDto.setPayMethod(PayOrderMethod.RECHARGE);
        //获取人民币汇率
        BigDecimal cnyRate = new BigDecimal("6.3");
        orderTransactionDto.setCnyRate(cnyRate);
        //余额支付订单
        BaseResponse response = umsFeignClient.accountPayment(request);
        if (response.getCode() != ResultCode.SUCCESS_CODE) {//支付失败
            return response.getMsg();
        }
        //修改订单状态
        orderDao.updateOrderAsPaid(orderTransactionDto);
        if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
            customerProductStockService.reduceFromLockStock(customerId, dischargeQuantityVos);
        }
        CustomerOrderDailyCountUpdateRequest customerOrderDailyCountUpdateRequest = new CustomerOrderDailyCountUpdateRequest();
        customerOrderDailyCountUpdateRequest.setCustomerId(customerId);
        customerOrderDailyCountUpdateRequest.setOrderType(TransactionConstant.OrderType.NORMAL_ORDER.getCode());
        customerOrderDailyCountUpdateRequest.setPaymentId(paymentId);
        customerOrderDailyCountUpdateRequest.setPayTime(payTime);
        orderDailyPayCountService.updateCustomerOrderDailyCount(customerOrderDailyCountUpdateRequest);

        String key = RedisKey.LIST_CUSTOMER_NORMAL_ORDER_PAYMENT_ID + customerId;
        redisTemplate.opsForList().rightPush(key,paymentId);
        return "success";
    }

    @Async
    public void payOrderAsync(Long userId, Long customerId, Long paymentId, Integer payMethod) {
        sendSaveTransactionRecordMessage(paymentId, customerId, userId, payMethod);
        // 订单上传赛盒 放在 sendSaveTransactionRecordMessage的消費端
        mqOnSaiheService.uploadPaymentIdOnMq(paymentId, OrderType.NORMAL);
        customerStockRecordService.saveDischargeStockRecordByPaymentId(customerId, paymentId, OrderType.NORMAL);
        customerProductSalesLogService.saveProductSaleRecord(paymentId, OrderType.NORMAL, customerId, new Date());
//        List<AppOrderVo> orderVos = orderDao.selectPayOrderListByPaymentId(paymentId);
//        if (ListUtils.isNotEmpty(orderVos)) {
//            for (AppOrderVo orderVo : orderVos) {
//                if (orderVo.getPayState() == 1) {
//                    orderProfitService.updateOrderProfit(orderVo.getId());
//                }
//            }
//        }

    }

    //发送保存交易流水的消息
    @Override
    public void sendSaveTransactionRecordMessage(Long paymentId, Long customerId, Long userId, Integer payMethod) {
        BigDecimal payAmount = orderDao.selectPayAmountByPaymentId(paymentId);
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(paymentId);
        detail.setUserId(userId);
        detail.setCustomerId(customerId);
        detail.setPayMethod(payMethod);
        detail.setPayAmount(payAmount);
        detail.setOrderType(OrderType.NORMAL);
        detail.setPayTime(new Date());

        List<TransactionDetail> transactionDetails = orderDao.selectTransactionDetailByPaymentId(paymentId);
        if (ListUtils.isEmpty(transactionDetails)) {
            return;
        }

        detail.setOrderTransactions(transactionDetails);

        umsFeignClient.saveTransactionDetails(detail);
    }


    @Override
    public BaseResponse createPaypalOrder(Session session, BigDecimal amount, Long paymentId, List<ItemDischargeQuantityVo> dischargeQuantityVos) {
        List<AppOrderVo> orderVos = orderDao.selectPayOrderListByPaymentId(paymentId);

        PaypalOrder paypalOrder = new PaypalOrder();
        paypalOrder.setId(paymentId);
        paypalOrder.setAccountId(session.getAccountId());
        paypalOrder.setOrderType(OrderType.NORMAL);
        paypalOrder.setSession(session);
        HttpServletRequest request = RequestUtil.getRequest();
        String origin = request.getHeader("Origin");
        paypalOrder.setSuccessUrl(origin + "/payment/sibPayPayPal");
        paypalOrder.setFailedUrl(origin + "/payment/sibPayPayPal");
        paypalOrder.setAccountPaymethodId(PayOrderMethod.PAYPAL);
        paypalOrder.setAmount(amount);
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal dischargeAmount = BigDecimal.ZERO;
        BigDecimal shipPrice = BigDecimal.ZERO;
        List<PaypalOrderItem> orderItems = new ArrayList<>();
        for (AppOrderVo orderVo : orderVos) {
            productAmount = productAmount.add(orderVo.getProductAmount());
            dischargeAmount = dischargeAmount.add(orderVo.getProductDischargeAmount());
            shipPrice = shipPrice.add(orderVo.getShipPrice().add(orderVo.getVatAmount()));
            Set<AppStoreOrderVo> storeOrderVos = orderVo.getStoreOrderVos();
            storeOrderVos.forEach(appStoreOrderVo -> {
                Set<AppOrderItemVo> orderItemVos = appStoreOrderVo.getItemVos();
                for (AppOrderItemVo itemVo : orderItemVos) {
                    PaypalOrderItem orderItem = new PaypalOrderItem();
                    orderItem.setItemId(itemVo.getId());
                    orderItem.setOrderId(itemVo.getOrderId());
                    orderItem.setPrice(itemVo.getPrice());
                    orderItem.setQuantity(itemVo.getQuantity());
                    orderItem.setSku(itemVo.getStoreVariantSku());
                    orderItems.add(orderItem);
                }
            });

        }
        paypalOrder.setDischargeAmount(dischargeAmount);
        paypalOrder.setShipPrice(shipPrice);
        paypalOrder.setProductAmount(productAmount);
        paypalOrder.setItems(orderItems);
        BaseResponse response = new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        try {
            response = umsFeignClient.getPaypalOrderUrl(paypalOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }

    @Transactional
    @Override
    public PaymentDetail payOrderByPaypal(Long userId, Long customerId, Long paymentId) {
        Date payTime = new Date();
        BigDecimal amount = orderDao.selectPayAmountByPaymentId(paymentId);
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(paymentId);
        detail.setUserId(userId);
        detail.setCustomerId(customerId);
        detail.setPayMethod(PayOrderMethod.PAYPAL);
        detail.setPayAmount(amount);
        detail.setOrderType(OrderType.NORMAL);
        detail.setPayTime(new Date());

        OrderTransactionDto orderTransactionDto = new OrderTransactionDto();
        orderTransactionDto.setFixFeePercentage(Constant.PAYPAL_FEE_PERCENTAGE);
        orderTransactionDto.setPaymentId(paymentId);
        orderTransactionDto.setPayTime(payTime);
        orderTransactionDto.setPayMethod(PayOrderMethod.PAYPAL);

        BigDecimal cnyRate = (BigDecimal) redisTemplate.opsForHash().get("currency:rate:USD", "cnyRate");
        if (null == cnyRate) {
            cnyRate = new BigDecimal((Double) umsFeignClient.getCurrencyRate("USD").getData());
        }
        orderTransactionDto.setCnyRate(cnyRate);

        orderDao.updateOrderAsPaid(orderTransactionDto);
        List<ItemDischargeQuantityVo> dischargeQuantityVos = orderItemDao.selectDischargeQuantityByPaymentId(paymentId);
        if (ListUtils.isNotEmpty(dischargeQuantityVos)) {
            customerProductStockDao.reduceFromLockStock(customerId, dischargeQuantityVos);
        }
        payOrderAsync(userId,customerId,paymentId,1);
        return detail;
    }


    @Override
    public boolean orderShipPriceCheck(List<AppOrderVo> orders) {
        Map<String, BigDecimal> map = new HashMap<>();
        List<BatchShipMethodSelectDto> batchShipMethodSelectDtos = new ArrayList<>();
        for (AppOrderVo order : orders) {
            BatchShipMethodSelectDto methodSelectDto = new BatchShipMethodSelectDto();
            BigDecimal weight = BigDecimal.ZERO;
            BigDecimal volume = BigDecimal.ZERO;
            Set<AppStoreOrderVo> storeOrderVos = order.getStoreOrderVos();
            for (AppStoreOrderVo storeOrderVo : storeOrderVos) {
                Set<AppOrderItemVo> itemVos = storeOrderVo.getItemVos();
                for (AppOrderItemVo itemVo : itemVos) {
                    BigDecimal quantity = new BigDecimal(itemVo.getQuantity());
                    weight = weight.add(quantity.multiply(itemVo.getAdminVariantWeight()));
                    volume = volume.add(quantity.multiply(itemVo.getAdminVariantVolume()));
                }
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
        List<BatchShipMethodSelectVo> batchShipMethodSelectVos = response.getBatchShipMethodSelectVos();
        for (BatchShipMethodSelectVo batchShipMethodSelectVo : batchShipMethodSelectVos) {
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

    //订单支付后发送订单上传到赛盒的MQ
    public void sendOrderUploadSaiheMessage(Long paymentId) {
        List<Order> orders = orderDao.selectOrderByPaymentId(paymentId);
        if (ListUtils.isNotEmpty(orders)) {
            List<Message> messages = new ArrayList<>();
            orders.forEach(order -> {
                if (order.getPayState() == 1
                        && order.getRefundState() == 0
                        && order.getShipState() == 0) {
                    Message message = new Message();
                    message.setTags(OrderType.NORMAL + "");
                    message.setTopic(RocketMqConfig.TOPIC_ORDER_UPLOAD_SAIHE);
                    message.setKeys(String.valueOf(order.getId()));
                    message.setBody(new byte[0]);
                    messages.add(message);
                }
            });
            if (ListUtils.isNotEmpty(messages)) {
                try {
//                    defaultMQProducer.send(messages);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.warn("paymentId:{},赛盒导入消息发送失败");
                }
            }
        }

    }

}
