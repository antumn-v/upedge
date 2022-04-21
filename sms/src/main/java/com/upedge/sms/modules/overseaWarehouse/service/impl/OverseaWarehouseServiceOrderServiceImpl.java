package com.upedge.sms.modules.overseaWarehouse.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ProductConstant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.cart.request.CartSelectByIdsRequest;
import com.upedge.common.model.cart.request.CartVo;
import com.upedge.common.model.oms.stock.StockOrderItemVo;
import com.upedge.common.model.oms.stock.StockOrderVo;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderCreateRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderListRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderPayRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderUpdateTrackingRequest;
import com.upedge.sms.modules.center.service.ServiceOrderFreightService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderItemService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;
import com.upedge.thirdparty.fpx.api.FpxWmsApi;
import com.upedge.thirdparty.fpx.request.CreateFpxInboundRequest;
import com.upedge.thirdparty.fpx.request.CreateFpxInboundRequest.IconsignmentSkuDTO;
import com.upedge.thirdparty.fpx.vo.FpxInbound;
import com.upedge.thirdparty.fpx.vo.FpxInboundSku;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;


@Service
public class OverseaWarehouseServiceOrderServiceImpl implements OverseaWarehouseServiceOrderService {

    @Autowired
    private OverseaWarehouseServiceOrderDao overseaWarehouseServiceOrderDao;

    @Autowired
    OverseaWarehouseServiceOrderItemService overseaWarehouseServiceOrderItemService;

    @Autowired
    ServiceOrderFreightService ServiceOrderFreightService;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OverseaWarehouseServiceOrder record = new OverseaWarehouseServiceOrder();
        record.setId(id);
        return overseaWarehouseServiceOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.insert(record);
    }

    @GlobalTransactional
    @Override
    public BaseResponse confirmReceipt(Long orderId, Session session) {
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = selectByPrimaryKey(orderId);
        if (null == overseaWarehouseServiceOrder
        || overseaWarehouseServiceOrder.getPayState() != OrderConstant.PAY_STATE_PAID
        || overseaWarehouseServiceOrder.getShipState() == 2){
            return BaseResponse.failed("订单不存在或订单未支付或已确认收货");
        }

        FpxInbound fpxInbound = FpxWmsApi.selectInboundByOrderId(orderId);
        if (null == fpxInbound){
            return BaseResponse.failed("fpx委托单不存在");
        }
        List<FpxInboundSku> fpxInboundSkus = fpxInbound.getLstsku();
        Map<String,Integer> skuQuantity = new HashMap<>();
        for (FpxInboundSku inboundSkus : fpxInboundSkus) {
            skuQuantity.put(inboundSkus.getSkuCode(),inboundSkus.getReceivedQty());
        }

        List<OverseaWarehouseServiceOrderItem> orderItems = overseaWarehouseServiceOrderItemService.selectByOrderId(orderId);

        StockOrderVo stockOrderVo = new StockOrderVo();
        BeanUtils.copyProperties(overseaWarehouseServiceOrder,stockOrderVo);
        stockOrderVo.setAmount(overseaWarehouseServiceOrder.getPayAmount());
        stockOrderVo.setShipReview(3);
        stockOrderVo.setPurchaseState(3);

        List<StockOrderItemVo> stockOrderItemVos = new ArrayList<>();
        for (OverseaWarehouseServiceOrderItem orderItem : orderItems) {
            if(StringUtils.isBlank(orderItem.getWarehouseSku())){
                return BaseResponse.failed("订单产品缺失sku");
            }
            Integer stock = skuQuantity.get(orderItem.getWarehouseSku());
            if(null == stock){
                return BaseResponse.failed("订单产品匹配海外仓sku异常");
            }
            StockOrderItemVo stockOrderItemVo = new StockOrderItemVo();
            BeanUtils.copyProperties(orderItem,stockOrderItemVo);
            stockOrderItemVo.setQuantity(stock);
            stockOrderItemVos.add(stockOrderItemVo);
        }
        stockOrderVo.setItems(stockOrderItemVos);
        BaseResponse baseResponse = omsFeignClient.orderConfirmReceipt(stockOrderVo);
        if (baseResponse.getCode() != ResultCode.SUCCESS_CODE){
            return baseResponse;
        }
        overseaWarehouseServiceOrder = new OverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrder.setId(orderId);
        overseaWarehouseServiceOrder.setShipState(OrderConstant.SHIP_STATE_RECEIPTED);
        overseaWarehouseServiceOrder.setUpdateTime(new Date());
        updateByPrimaryKeySelective(overseaWarehouseServiceOrder);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(orderId);
        serviceOrder.setServiceState(1);
        serviceOrder.setUpdateTime(new Date());
        serviceOrder.setFinishTime(new Date());
        serviceOrder.setManagerId(session.getId());
        serviceOrderService.updateByPrimaryKeySelective(serviceOrder);
        return baseResponse;
    }

    @Override
    public BaseResponse updateTrackingCode(OverseaWarehouseServiceOrderUpdateTrackingRequest request) {
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = selectByPrimaryKey(request.getOrderId());
        if (null == overseaWarehouseServiceOrder){
            return BaseResponse.failed("订单不存在");
        }
        Integer shipState = overseaWarehouseServiceOrder.getShipState();
        overseaWarehouseServiceOrder = new OverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrder.setId(request.getOrderId());
        overseaWarehouseServiceOrder.setTrackingCode(request.getTrackingCode());
        if (0 == shipState){
            overseaWarehouseServiceOrder.setShipState(1);
        }
        updateByPrimaryKeySelective(overseaWarehouseServiceOrder);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse createFpxInbound(Long id, Session session) {
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = selectByPrimaryKey(id);
        if (null == overseaWarehouseServiceOrder
        || 1 != overseaWarehouseServiceOrder.getPayState()){
            return BaseResponse.failed("订单不存在或订单未支付");
        }
        List<OverseaWarehouseServiceOrderItem> orderItems = overseaWarehouseServiceOrderItemService.selectByOrderId(id);

        CreateFpxInboundRequest createFpxInboundRequest = new CreateFpxInboundRequest();
        createFpxInboundRequest.setRefNo(id.toString());
        createFpxInboundRequest.setToWarehouseCode(overseaWarehouseServiceOrder.getWarehouseCode());
        List<IconsignmentSkuDTO> iconsignmentSkus = new ArrayList<>();
        String boxNo = IdGenerate.uuid();
        String boxCode = IdGenerate.nextId().toString();
        for (OverseaWarehouseServiceOrderItem orderItem : orderItems) {
            if (StringUtils.isBlank(orderItem.getWarehouseSku())){
                return BaseResponse.failed("有产品未上传海外仓");
            }
            IconsignmentSkuDTO skuDTO = new IconsignmentSkuDTO();
            skuDTO.setBoxNo(boxNo);
            skuDTO.setBoxBarcode(boxCode);
            skuDTO.setSkuCode(orderItem.getWarehouseSku());
            skuDTO.setPlanQty(orderItem.getQuantity());
            iconsignmentSkus.add(skuDTO);
        }
        String result = FpxWmsApi.createInbound(createFpxInboundRequest);
        return BaseResponse.success();
    }

    @Override
    public List<OverseaWarehouseServiceOrderVo> selectAllUnPaidList() {
        return overseaWarehouseServiceOrderDao.selectAllUnPaidList();
    }

    @Override
    public List<OverseaWarehouseServiceOrderVo> orderList(OverseaWarehouseServiceOrderListRequest request) {
        List<OverseaWarehouseServiceOrder> overseaWarehouseServiceOrders = select(request);
        if (ListUtils.isEmpty(overseaWarehouseServiceOrders)){
            return new ArrayList<>();
        }
        List<OverseaWarehouseServiceOrderVo> overseaWarehouseServiceOrderVos = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(overseaWarehouseServiceOrders.size());
        for (OverseaWarehouseServiceOrder overseaWarehouseServiceOrder : overseaWarehouseServiceOrders) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        overseaWarehouseServiceOrderVos.add(orderDetail(overseaWarehouseServiceOrder.getId()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        latch.countDown();
                    }
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return overseaWarehouseServiceOrderVos;
    }

    @GlobalTransactional
    @Override
    public BaseResponse orderPay(OverseaWarehouseServiceOrderPayRequest request, Session session) {
        //检查状态
        Long orderId = request.getOrderId();
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = selectByPrimaryKey(orderId);
        if (null == overseaWarehouseServiceOrder
        || overseaWarehouseServiceOrder.getPayState() != 0){
            return BaseResponse.failed("Order does not exist or has been paid!");
        }
        //检查运费
        Integer shipType = request.getShipType();
        BigDecimal shipPrice = request.getShipPrice();
        ServiceOrderFreight ServiceOrderFreight = ServiceOrderFreightService.selectByOrderIdAndShipType(orderId, shipType);
        if (null == ServiceOrderFreight
        || shipPrice.compareTo(ServiceOrderFreight.getShipPrice()) != 0){
            return BaseResponse.failed("Shipping has been updated, please refresh the page");
        }
        //检查支付金额
        BigDecimal payAmount = request.getPayAmount();
        BigDecimal productAmount = overseaWarehouseServiceOrder.getProductAmount();
        if (payAmount.compareTo(productAmount.add(shipPrice)) != 0){
            return BaseResponse.failed("Incorrect payment amount!");
        }
        //账户支付
        Long paymentId = IdGenerate.nextId();
        AccountPaymentRequest accountPaymentRequest = new AccountPaymentRequest(paymentId,session.getId(),session.getAccountId(),session.getCustomerId(),OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE,payAmount,BigDecimal.ZERO,0);
        BaseResponse paymentResponse = umsFeignClient.accountPayment(accountPaymentRequest);
        if (paymentResponse.getCode() != ResultCode.SUCCESS_CODE){
            return paymentResponse;
        }
        //修改状态
        Date payTime = new Date();
        overseaWarehouseServiceOrder.setPaymentId(paymentId);
        overseaWarehouseServiceOrder.setPayAmount(payAmount);
        overseaWarehouseServiceOrder.setPayTime(payTime);
        overseaWarehouseServiceOrder.setShipPrice(shipPrice);
        overseaWarehouseServiceOrder.setShipType(shipType);
        overseaWarehouseServiceOrderDao.updateOrderAsPaid(overseaWarehouseServiceOrder);
        serviceOrderService.updateToPaidByRelateId(orderId,OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE,payAmount,payTime);
//        overseaWarehouseServiceOrderItemService.updateWarehouseSkuByOrderId(orderId);
        //发送消息
        sendSaveTransactionRecordMessage(session.getId(),overseaWarehouseServiceOrder);
        return BaseResponse.success();
    }

    @Override
    public OverseaWarehouseServiceOrderVo orderDetail(Long orderId) {
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = selectByPrimaryKey(orderId);
        if (null == overseaWarehouseServiceOrder){
            return null;
        }
        OverseaWarehouseServiceOrderVo overseaWarehouseServiceOrderVo = new OverseaWarehouseServiceOrderVo();
        BeanUtils.copyProperties(overseaWarehouseServiceOrder,overseaWarehouseServiceOrderVo);

        List<OverseaWarehouseServiceOrderItem> orderItems = overseaWarehouseServiceOrderItemService.selectByOrderId(orderId);
        overseaWarehouseServiceOrderVo.setOrderItems(orderItems);

        List<ServiceOrderFreight> orderFreights = ServiceOrderFreightService.selectByOrderId(orderId);
        overseaWarehouseServiceOrderVo.setOrderFreights(orderFreights);

        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + overseaWarehouseServiceOrder.getWarehouseCode());
        overseaWarehouseServiceOrderVo.setWarehouseName(warehouseVo.getWarehouseEnEame());
        return overseaWarehouseServiceOrderVo;
    }

    @Transactional
    @Override
    public BaseResponse create(OverseaWarehouseServiceOrderCreateRequest request, Session session) {
        String warehouseCode = request.getWarehouseCode();
        if (warehouseCode.equals(ProductConstant.DEFAULT_WAREHOUSE_ID)){
            return BaseResponse.failed();
        }
        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + warehouseCode);
        if (null == warehouseVo){
            return BaseResponse.failed("Warehouse error!");
        }
        CartSelectByIdsRequest cartSelectByIdsRequest = new CartSelectByIdsRequest();
        cartSelectByIdsRequest.setIds(request.getCartIds());
        cartSelectByIdsRequest.setCartType(0);
        cartSelectByIdsRequest.setCustomerId(session.getCustomerId());
        List<CartVo> cartVos = omsFeignClient.selectByIds(cartSelectByIdsRequest);
        if (ListUtils.isEmpty(cartVos)){
            return BaseResponse.failed();
        }
        Long orderId = IdGenerate.nextId();
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalVolume = BigDecimal.ZERO;
        List<OverseaWarehouseServiceOrderItem> orderItems = new ArrayList<>();
        for (CartVo cartVo : cartVos) {
            BigDecimal quantity = new BigDecimal(cartVo.getQuantity());
            OverseaWarehouseServiceOrderItem orderItem = new OverseaWarehouseServiceOrderItem();
            BeanUtils.copyProperties(cartVo,orderItem);
            orderItem.setOrderId(orderId);
            orderItem.setId(IdGenerate.nextId());
            orderItems.add(orderItem);
            productAmount = productAmount.add(quantity.multiply(cartVo.getPrice()));
            totalWeight = totalWeight.add(quantity.multiply(cartVo.getVariantWeight()));
            totalVolume = totalVolume.add(quantity.multiply(cartVo.getVariantVolume()));
        }
        overseaWarehouseServiceOrderItemService.insertByBatch(orderItems);

        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = new OverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrder.init();
        overseaWarehouseServiceOrder.setWarehouseCode(request.getWarehouseCode());
        overseaWarehouseServiceOrder.setCustomerId(session.getCustomerId());
        overseaWarehouseServiceOrder.setId(orderId);
        overseaWarehouseServiceOrder.setTotalWeight(totalWeight);
        overseaWarehouseServiceOrder.setTotalVolume(totalVolume);
        overseaWarehouseServiceOrder.setProductAmount(productAmount);
        overseaWarehouseServiceOrderDao.insert(overseaWarehouseServiceOrder);

        List<ServiceOrderFreight> orderFreights = new ArrayList<>();
        List<Integer> shipTypes = request.getLogistics();;
        for (Integer shipType : shipTypes) {
            ServiceOrderFreight ServiceOrderFreight = new ServiceOrderFreight();
            ServiceOrderFreight.setId(IdGenerate.nextId());
            ServiceOrderFreight.setOrderId(orderId);
            ServiceOrderFreight.setShipType(shipType);
            orderFreights.add(ServiceOrderFreight);
        }
        ServiceOrderFreightService.insertByBatch(orderFreights);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setServiceTitle(request.getServiceTitle());
        serviceOrder.setId(orderId);
        serviceOrder.setRelateId(orderId);
        serviceOrder.setServiceState(0);
        serviceOrder.setCustomerId(session.getCustomerId());
        serviceOrder.setCreateTime(new Date());
        serviceOrder.setPayState(0);
        serviceOrder.setRefundState(0);
        serviceOrder.setServiceType(OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE);
        serviceOrder.setUpdateTime(new Date());
        serviceOrderService.insert(serviceOrder);
        omsFeignClient.submitByIds(request.getCartIds());
        return BaseResponse.success();
    }

    /**
     *
     */
    public OverseaWarehouseServiceOrder selectByPrimaryKey(Long id){
        OverseaWarehouseServiceOrder record = new OverseaWarehouseServiceOrder();
        record.setId(id);
        return overseaWarehouseServiceOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OverseaWarehouseServiceOrder> select(Page<OverseaWarehouseServiceOrder> record){
        record.initFromNum();
        return overseaWarehouseServiceOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OverseaWarehouseServiceOrder> record){
        return overseaWarehouseServiceOrderDao.count(record);
    }


    public void sendSaveTransactionRecordMessage(Long userId, OverseaWarehouseServiceOrder order) {
        PaymentDetail detail = new PaymentDetail();
        detail.setPaymentId(order.getPaymentId());
        detail.setUserId(userId);
        detail.setCustomerId(order.getCustomerId());
        detail.setPayMethod(0);
        detail.setPayAmount(order.getPayAmount());
        detail.setOrderType(OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE);
        detail.setPayTime(order.getPayTime());

        List<TransactionDetail> transactionDetails = new ArrayList<>();
        TransactionDetail transactionDetail = new TransactionDetail();
        transactionDetail.setOrderId(order.getId());
        transactionDetail.setPaymentId(order.getPaymentId());
        transactionDetail.setPayTime(order.getPayTime());
        transactionDetail.setAmount(order.getPayAmount());
        transactionDetail.setOrderType(OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE);
        transactionDetails.add(transactionDetail);

        detail.setOrderTransactions(transactionDetails);

        umsFeignClient.saveTransactionDetails(detail);

    }

}