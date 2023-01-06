package com.upedge.sms.modules.wholesale.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderConstant;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.account.AccountPaymentRequest;
import com.upedge.common.model.cart.request.CartSelectByIdsRequest;
import com.upedge.common.model.cart.request.CartSubmitRequest;
import com.upedge.common.model.cart.request.CartVo;
import com.upedge.common.model.oms.stock.CustomerStockSearchRequest;
import com.upedge.common.model.oms.stock.CustomerStockVo;
import com.upedge.common.model.order.PaymentDetail;
import com.upedge.common.model.order.TransactionDetail;
import com.upedge.common.model.sms.WholesaleOrderItemDischargeStockVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import com.upedge.sms.modules.center.service.ServiceOrderFreightService;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.wholesale.WholesaleOrderVo;
import com.upedge.sms.modules.wholesale.dao.WholesaleOrderDao;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderCreateRequest;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderPayRequest;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderAddressService;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderItemService;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class WholesaleOrderServiceImpl implements WholesaleOrderService {

    @Autowired
    private WholesaleOrderDao wholesaleOrderDao;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    WholesaleOrderItemService wholesaleOrderItemService;

    @Autowired
    WholesaleOrderAddressService wholesaleOrderAddressService;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Autowired
    ServiceOrderFreightService serviceOrderFreightService;

    @Autowired
    UmsFeignClient umsFeignClient;

    @Autowired
    PmsFeignClient pmsFeignClient;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WholesaleOrder record = new WholesaleOrder();
        record.setId(id);
        return wholesaleOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WholesaleOrder record) {
        return wholesaleOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WholesaleOrder record) {
        return wholesaleOrderDao.insert(record);
    }

    @GlobalTransactional
    @Override
    public BaseResponse payOrder(WholesaleOrderPayRequest request, Session session) {
        //检查状态
        Long orderId = request.getOrderId();
        WholesaleOrder wholesaleOrder = selectByPrimaryKey(orderId);
        if (null == wholesaleOrder
                || wholesaleOrder.getPayState() != 0) {
            return BaseResponse.failed("Order does not exist or has been paid!");
        }
        //检查运费
        Integer shipType = request.getShipType();
        BigDecimal shipPrice = request.getShipPrice();
        ServiceOrderFreight ServiceOrderFreight = serviceOrderFreightService.selectByOrderIdAndShipType(orderId, shipType);
        if (null == ServiceOrderFreight
                || shipPrice.compareTo(ServiceOrderFreight.getShipPrice()) != 0) {
            return BaseResponse.failed("Shipping has been updated, please refresh the page");
        }
        //检查支付金额
        BigDecimal payAmount = request.getPayAmount();
        BigDecimal productAmount = wholesaleOrder.getProductAmount();
        BigDecimal dischargeAmount = checkDischargeDetail(wholesaleOrder);
        if(null == dischargeAmount){
            return BaseResponse.failed("Abnormal inventory");
        }
        if (payAmount.compareTo(productAmount.add(shipPrice).subtract(dischargeAmount)) != 0) {
            return BaseResponse.failed("Incorrect payment amount!");
        }
        //账户支付
        Long paymentId = IdGenerate.nextId();
        AccountPaymentRequest accountPaymentRequest = new AccountPaymentRequest(paymentId, session.getId(), session.getAccountId(), session.getCustomerId(), OrderType.EXTRA_SERVICE_OVERSEA_WAREHOUSE, payAmount, BigDecimal.ZERO, 0);
        BaseResponse paymentResponse = umsFeignClient.accountPayment(accountPaymentRequest);
        if (paymentResponse.getCode() != ResultCode.SUCCESS_CODE) {
            return paymentResponse;
        }
        //修改状态
        Date payTime = new Date();
        wholesaleOrder.setPaymentId(paymentId);
        wholesaleOrder.setPayAmount(payAmount);
        wholesaleOrder.setPayTime(payTime);
        wholesaleOrder.setShipPrice(shipPrice);
        wholesaleOrder.setShipType(shipType);
        wholesaleOrderDao.updateOrderAsPaid(wholesaleOrder);
        serviceOrderService.updateToPaidByRelateId(orderId, OrderType.EXTRA_SERVICE_WHOLESALE, payAmount, payTime);
        //发送消息
//        sendSaveTransactionRecordMessage(session.getId(),wholesaleOrder);
        return BaseResponse.success();
    }

    private BigDecimal checkDischargeDetail(WholesaleOrder wholesaleOrder) {
        if (wholesaleOrder.getPayState() != 0) {
            return null;
        }
        Long customerId = wholesaleOrder.getCustomerId();
        List<WholesaleOrderItem> orderItems = wholesaleOrderItemService.selectByOrderId(wholesaleOrder.getId());
        List<Long> variantIds = new ArrayList<>();
        for (WholesaleOrderItem orderItem : orderItems) {
            variantIds.add(orderItem.getVariantId());
        }
        BigDecimal dischargeAmount = BigDecimal.ZERO;
        CustomerStockSearchRequest request = new CustomerStockSearchRequest();
        request.setCustomerId(customerId);
        request.setVariantIds(variantIds);
        List<CustomerStockVo> customerStockVos = omsFeignClient.searchByVariants(request);
        for (CustomerStockVo customerStockVo : customerStockVos) {
            customerStockVo.setTotalStock(customerStockVo.getStock());
        }

        Integer dischargeQuantity = 0;
        Integer stock = 0;
        if (ListUtils.isEmpty(customerStockVos)) {
            return BigDecimal.ZERO;
        }
        List<WholesaleOrderItemDischargeStockVo> wholesaleOrderItemDischargeStockVos = new ArrayList<>();
        a:
        for (WholesaleOrderItem orderItem : orderItems) {

            dischargeQuantity = orderItem.getDischargeQuantity();
            if (dischargeQuantity == null) {
                dischargeQuantity = 0;
            }
            if (dischargeQuantity == 0) {
                continue;
            }
            WholesaleOrderItemDischargeStockVo wholesaleOrderItemDischargeStockVo = new WholesaleOrderItemDischargeStockVo();
            wholesaleOrderItemDischargeStockVo.setDischargeQuantity(dischargeQuantity);
            wholesaleOrderItemDischargeStockVo.setVariantId(orderItem.getVariantId());
            wholesaleOrderItemDischargeStockVo.setCustomerId(customerId);
            wholesaleOrderItemDischargeStockVo.setId(orderItem.getId());
            wholesaleOrderItemDischargeStockVos.add(wholesaleOrderItemDischargeStockVo);

            for (CustomerStockVo customerStockVo : customerStockVos) {
                if (orderItem.getVariantId().equals(customerStockVo.getVariantId())) {
                    stock = customerStockVo.getStock();
                    if (stock >= dischargeQuantity) {
                        stock -= dischargeQuantity;
                    } else {
                        return null;
                    }
                    customerStockVo.setStock(stock);
                    dischargeAmount = dischargeAmount.add(new BigDecimal(dischargeQuantity).multiply(orderItem.getPrice()));

                    continue a;
                }
            }
            return null;
        }
        BaseResponse baseResponse = omsFeignClient.reduceByWholesale(wholesaleOrderItemDischargeStockVos);
        if (baseResponse.getCode() != ResultCode.SUCCESS_CODE){
            return null;
        }
        wholesaleOrderDao.updateDischargeAmountById(wholesaleOrder.getId(), dischargeAmount);
        return dischargeAmount;
    }

    @Override
    public WholesaleOrderVo orderDetail(Long orderId) {
        WholesaleOrder wholesaleOrder = selectByPrimaryKey(orderId);
        if (null == wholesaleOrder) {
            return null;
        }
        WholesaleOrderVo wholesaleOrderVo = new WholesaleOrderVo();
        BeanUtils.copyProperties(wholesaleOrder, wholesaleOrderVo);

        WholesaleOrderAddress wholesaleOrderAddress = wholesaleOrderAddressService.selectByOrderId(orderId);
        wholesaleOrderVo.setAddress(wholesaleOrderAddress);

        List<WholesaleOrderItem> orderItems = wholesaleOrderItemService.selectByOrderId(orderId);
        if (wholesaleOrder.getPayState() == 0) {
            checkStock(orderItems, wholesaleOrderVo);
        }
        wholesaleOrderVo.setWholesaleOrderItems(orderItems);

        List<ServiceOrderFreight> freights = serviceOrderFreightService.selectByOrderId(orderId);
        wholesaleOrderVo.setOrderFreights(freights);
        return wholesaleOrderVo;
    }


    private void checkStock(List<WholesaleOrderItem> orderItems, WholesaleOrderVo wholesaleOrderVo) {
        List<Long> variantIds = new ArrayList<>();
        for (WholesaleOrderItem orderItem : orderItems) {
            variantIds.add(orderItem.getVariantId());
        }
        BigDecimal dischargeAmount = BigDecimal.ZERO;
        CustomerStockSearchRequest request = new CustomerStockSearchRequest();
        request.setCustomerId(wholesaleOrderVo.getCustomerId());
        request.setVariantIds(variantIds);
        List<CustomerStockVo> customerStockVos = omsFeignClient.searchByVariants(request);
        for (CustomerStockVo customerStockVo : customerStockVos) {
            customerStockVo.setTotalStock(customerStockVo.getStock());
        }
        Integer quantity = 0;
        Integer dischargeQuantity = 0;
        Integer stock = 0;
        if (ListUtils.isNotEmpty(customerStockVos)) {
            a:
            for (WholesaleOrderItem orderItem : orderItems) {
                dischargeQuantity = orderItem.getDischargeQuantity();
                quantity = orderItem.getQuantity();
                if (dischargeQuantity == null) {
                    dischargeQuantity = 0;
                }
                for (CustomerStockVo customerStockVo : customerStockVos) {
                    if (orderItem.getVariantId().equals(customerStockVo.getVariantId())) {
                        stock = customerStockVo.getStock();
                        if (stock > quantity) {
                            if (dischargeQuantity == 0) {
                                dischargeQuantity = quantity;
                            }
                            stock -= dischargeQuantity;

                        } else {
                            dischargeQuantity = stock;
                            stock = 0;
                        }
                        orderItem.setDischargeQuantity(dischargeQuantity);
                        orderItem.setTotalStock(customerStockVo.getTotalStock());
                        customerStockVo.setStock(stock);
                        wholesaleOrderItemService.updateDischargeQuantityById(orderItem.getId(), dischargeQuantity);
                        dischargeAmount = dischargeAmount.add(new BigDecimal(dischargeQuantity).multiply(orderItem.getPrice()));

                        continue a;
                    }
                }
            }
        }else {
            for (WholesaleOrderItem orderItem : orderItems) {
                orderItem.setTotalStock(0);
                orderItem.setDischargeQuantity(0);
                wholesaleOrderItemService.updateDischargeQuantityById(orderItem.getId(), 0);
            }
        }
        wholesaleOrderVo.setProductDischargeAmount(dischargeAmount);
    }

    @Transactional
    @Override
    public BaseResponse create(WholesaleOrderCreateRequest request, Session session) {

        CartSelectByIdsRequest cartSelectByIdsRequest = new CartSelectByIdsRequest();
        cartSelectByIdsRequest.setIds(request.getCartIds());
        cartSelectByIdsRequest.setCustomerId(session.getCustomerId());
        List<CartVo> cartVos = omsFeignClient.selectByIds(cartSelectByIdsRequest);
        if (ListUtils.isEmpty(cartVos)) {
            return BaseResponse.failed();
        }
        Long orderId = IdGenerate.nextId();
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalVolume = BigDecimal.ZERO;
        List<WholesaleOrderItem> orderItems = new ArrayList<>();
        for (CartVo cartVo : cartVos) {
            BigDecimal quantity = new BigDecimal(cartVo.getQuantity());
            WholesaleOrderItem orderItem = new WholesaleOrderItem();
            BeanUtils.copyProperties(cartVo, orderItem);
            orderItem.setOrderId(orderId);
            orderItem.setId(IdGenerate.nextId());
            orderItem.setCartId(cartVo.getId());
            orderItems.add(orderItem);
            productAmount = productAmount.add(quantity.multiply(cartVo.getPrice()));
            totalWeight = totalWeight.add(quantity.multiply(cartVo.getVariantWeight()));
            totalVolume = totalVolume.add(quantity.multiply(cartVo.getVariantVolume()));
        }
        wholesaleOrderItemService.insertByBatch(orderItems);

        WholesaleOrder wholesaleOrder = new WholesaleOrder();
        wholesaleOrder.init();
        wholesaleOrder.setCustomerId(session.getCustomerId());
        wholesaleOrder.setId(orderId);
        wholesaleOrder.setTotalWeight(totalWeight);
        wholesaleOrder.setVolumeWeight(totalVolume);
        wholesaleOrder.setProductAmount(productAmount);
        wholesaleOrderDao.insert(wholesaleOrder);

        WholesaleOrderAddress wholesaleOrderAddress = request.getAddress();
        wholesaleOrderAddress.setOrderId(orderId);
        wholesaleOrderAddress.setId(IdGenerate.nextId());
        wholesaleOrderAddressService.insert(wholesaleOrderAddress);

        List<ServiceOrderFreight> orderFreights = new ArrayList<>();
        List<Integer> shipTypes = request.getLogistics();

        for (Integer shipType : shipTypes) {
            ServiceOrderFreight serviceOrderFreight = new ServiceOrderFreight();
            serviceOrderFreight.setId(IdGenerate.nextId());
            serviceOrderFreight.setOrderId(orderId);
            serviceOrderFreight.setShipType(shipType);
            serviceOrderFreight.setServiceType(OrderType.EXTRA_SERVICE_WHOLESALE);
            orderFreights.add(serviceOrderFreight);
        }
        serviceOrderFreightService.insertByBatch(orderFreights);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setServiceTitle(request.getServiceTitle());
        serviceOrder.setId(orderId);
        serviceOrder.setRelateId(orderId);
        serviceOrder.setServiceState(0);
        serviceOrder.setCustomerId(session.getCustomerId());
        serviceOrder.setCreateTime(new Date());
        serviceOrder.setPayState(0);
        serviceOrder.setRefundState(0);
        serviceOrder.setServiceType(OrderType.EXTRA_SERVICE_WHOLESALE);
        serviceOrder.setUpdateTime(new Date());
        serviceOrder.setRemark(request.getRemark());
        serviceOrderService.insert(serviceOrder);

        CartSubmitRequest cartSubmitRequest = new CartSubmitRequest();
        cartSubmitRequest.setType(1);
        cartSubmitRequest.setIds(request.getCartIds());
        omsFeignClient.submitByIds(cartSubmitRequest);

        return BaseResponse.success(wholesaleOrder);
    }

    /**
     *
     */
    public WholesaleOrder selectByPrimaryKey(Long id) {
        WholesaleOrder record = new WholesaleOrder();
        record.setId(id);
        return wholesaleOrderDao.selectByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKeySelective(WholesaleOrder record) {
        return wholesaleOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
     *
     */
    @Transactional
    public int updateByPrimaryKey(WholesaleOrder record) {
        return wholesaleOrderDao.updateByPrimaryKey(record);
    }

    /**
     *
     */
    public List<WholesaleOrder> select(Page<WholesaleOrder> record) {
        record.initFromNum();
        return wholesaleOrderDao.select(record);
    }

    /**
     *
     */
    public long count(Page<WholesaleOrder> record) {
        return wholesaleOrderDao.count(record);
    }


    @Override
    public void saveTransactionRecordMessage(Long userId, Long orderId) {
        WholesaleOrder order = selectByPrimaryKey(orderId);
        if (order.getPayState() != OrderConstant.PAY_STATE_PAID) {
            return;
        }
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