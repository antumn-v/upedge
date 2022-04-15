package com.upedge.sms.modules.overseaWarehouse.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.cart.request.CartSelectByIdsRequest;
import com.upedge.common.model.cart.request.CartVo;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderCreateRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderPayRequest;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderFreightService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderItemService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OverseaWarehouseServiceOrderServiceImpl implements OverseaWarehouseServiceOrderService {

    @Autowired
    private OverseaWarehouseServiceOrderDao overseaWarehouseServiceOrderDao;

    @Autowired
    OverseaWarehouseServiceOrderItemService overseaWarehouseServiceOrderItemService;

    @Autowired
    OverseaWarehouseServiceOrderFreightService overseaWarehouseServiceOrderFreightService;

    @Autowired
    ServiceOrderService serviceOrderService;

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

    @Override
    public List<OverseaWarehouseServiceOrderVo> selectAllUnPaidList() {
        return overseaWarehouseServiceOrderDao.selectAllUnPaidList();
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
        OverseaWarehouseServiceOrderFreight overseaWarehouseServiceOrderFreight = overseaWarehouseServiceOrderFreightService.selectByOrderIdAndShipType(orderId, shipType);
        if (null == overseaWarehouseServiceOrderFreight
        || shipPrice.compareTo(overseaWarehouseServiceOrderFreight.getShipPrice()) != 0){
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
        overseaWarehouseServiceOrderItemService.updateWarehouseSkuByOrderId(orderId);
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

        List<OverseaWarehouseServiceOrderFreight> orderFreights = overseaWarehouseServiceOrderFreightService.selectByOrderId(orderId);
        overseaWarehouseServiceOrderVo.setOrderFreights(orderFreights);
        return overseaWarehouseServiceOrderVo;
    }

    @Transactional
    @Override
    public BaseResponse create(OverseaWarehouseServiceOrderCreateRequest request, Session session) {
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
        overseaWarehouseServiceOrder.setCustomerId(session.getCustomerId());
        overseaWarehouseServiceOrder.setId(orderId);
        overseaWarehouseServiceOrder.setTotalWeight(totalWeight);
        overseaWarehouseServiceOrder.setTotalVolume(totalVolume);
        overseaWarehouseServiceOrder.setProductAmount(productAmount);
        overseaWarehouseServiceOrderDao.insert(overseaWarehouseServiceOrder);

        List<OverseaWarehouseServiceOrderFreight> orderFreights = new ArrayList<>();
        List<Integer> shipTypes = request.getLogistics();;
        for (Integer shipType : shipTypes) {
            OverseaWarehouseServiceOrderFreight overseaWarehouseServiceOrderFreight = new OverseaWarehouseServiceOrderFreight();
            overseaWarehouseServiceOrderFreight.setId(IdGenerate.nextId());
            overseaWarehouseServiceOrderFreight.setOrderId(orderId);
            overseaWarehouseServiceOrderFreight.setShipType(shipType);
            overseaWarehouseServiceOrderFreight.setShipPrice(BigDecimal.ZERO);
            orderFreights.add(overseaWarehouseServiceOrderFreight);
        }
        overseaWarehouseServiceOrderFreightService.insertByBatch(orderFreights);

        ServiceOrder serviceOrder = new ServiceOrder();
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

        Message message = new Message(RocketMqConfig.TOPIC_SAVE_ORDER_TRANSACTION, "oversea_warehouse_service_order", "oversea:warehouse:service:order:" + order.getPaymentId(), JSON.toJSONBytes(detail));
        message.setDelayTimeLevel(1);
        umsFeignClient.sendMessage(message);

    }

}