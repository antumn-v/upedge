package com.upedge.sms.modules.wholesale.request;

import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class WholesaleOrderUpdateRequest{

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private BigDecimal payAmount;
    /**
     * 
     */
    private Integer shipType;
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
    private BigDecimal volumeWeight;
    /**
     * 
     */
    private BigDecimal productAmount;
    /**
     * 
     */
    private BigDecimal productDischargeAmount;
    /**
     * 0=balance,1=paypal
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
     * 支付状态,待支付=0，已支付=1，取消订单=-1，支付中=2
     */
    private Integer payState;
    /**
     * 退款状态:0=未退款，1=申请中 2=部分退款，3=全部退款
     */
    private Integer refundState;
    /**
     * 0=未发货。1=已发货。
     */
    private Integer shipState;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 运输单号
     */
    private String trackingCode;

    public WholesaleOrder toWholesaleOrder(Long id){
        WholesaleOrder wholesaleOrder=new WholesaleOrder();
        wholesaleOrder.setId(id);
        wholesaleOrder.setCustomerId(customerId);
        wholesaleOrder.setPayAmount(payAmount);
        wholesaleOrder.setShipType(shipType);
        wholesaleOrder.setShipPrice(shipPrice);
        wholesaleOrder.setTotalWeight(totalWeight);
        wholesaleOrder.setVolumeWeight(volumeWeight);
        wholesaleOrder.setProductAmount(productAmount);
        wholesaleOrder.setProductDischargeAmount(productDischargeAmount);
        wholesaleOrder.setPayMethod(payMethod);
        wholesaleOrder.setPayTime(payTime);
        wholesaleOrder.setPaymentId(paymentId);
        wholesaleOrder.setPayState(payState);
        wholesaleOrder.setRefundState(refundState);
        wholesaleOrder.setShipState(shipState);
        wholesaleOrder.setCreateTime(createTime);
        wholesaleOrder.setUpdateTime(updateTime);
        wholesaleOrder.setTrackingCode(trackingCode);
        return wholesaleOrder;
    }

}
