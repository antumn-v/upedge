package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private Long storeId;
    /**
     * 
     */
    private BigDecimal payAmount;
    /**
     * 
     */
    private Long shipMethodId;
    /**
     * 
     */
    private BigDecimal shipPrice;
    /**
     * 
     */
    private BigDecimal totalWeight;
    /**
     * 
     */
    private BigDecimal productAmount;
    /**
     * 
     */
    private BigDecimal productDischargeAmount;
    /**
     * 
     */
    private BigDecimal fixFee;
    /**
     * 手续费百分比
     */
    private BigDecimal fixFeePercentage;
    /**
     * 
     */
    private Integer payMethod;
    /**
     * 
     */
    private Date payTime;
    /**
     * 
     */
    private Long paymentId;
    /**
     * 
     */
    private Integer payState;
    /**
     * 
     */
    private Integer refundState;
    /**
     * 
     */
    private Integer shipState;
    /**
     * 订单类型  正常订单=0 补发订单=1 拆分订单=2 合并订单=3
     */
    private Integer orderType;
    /**
     * 
     */
    private Long toAreaId;
    /**
     * 支付时美元汇率
     */
    private BigDecimal cnyRate;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public Order toOrder(Long id){
        Order order=new Order();
        order.setId(id);
        order.setCustomerId(customerId);
        order.setStoreId(storeId);
        order.setPayAmount(payAmount);
        order.setShipMethodId(shipMethodId);
        order.setShipPrice(shipPrice);
        order.setTotalWeight(totalWeight);
        order.setProductAmount(productAmount);
        order.setProductDischargeAmount(productDischargeAmount);
        order.setFixFee(fixFee);
        order.setFixFeePercentage(fixFeePercentage);
        order.setPayMethod(payMethod);
        order.setPayTime(payTime);
        order.setPaymentId(paymentId);
        order.setPayState(payState);
        order.setRefundState(refundState);
        order.setShipState(shipState);
        order.setOrderType(orderType);
        order.setToAreaId(toAreaId);
        order.setCnyRate(cnyRate);
        order.setCreateTime(createTime);
        order.setUpdateTime(updateTime);
        return order;
    }

}
