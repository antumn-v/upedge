package com.upedge.oms.modules.wholesale.request;

import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class WholesaleOrderAddRequest{

    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Long addressId;
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
    private BigDecimal vatAmount;
    /**
    * 
    */
    private BigDecimal productDischargeAmount;
    /**
    * 
    */
    private BigDecimal fixFee;
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
    * -1=已取消。0=未支付。1=已支付。2=退款申请中。3=部分退款。4=全部退款  5=支付中
    */
    private Integer payState;
    /**
    * 
    */
    private Integer refundState;
    /**
    * 0=未发货。1=已发货。
    */
    private Integer shipState;
    /**
    * 
    */
    private Long toAreaId;
    /**
    * 
    */
    private Date createTime;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 
    */
    private String saiheOrderCode;
    /**
    *   正常 = 0,待审查 = 1,作废 = 2
    */
    private Integer orderStatus;
    /**
    * 0=普通订单，1=excel导入
    */
    private Integer orderType;

    public WholesaleOrder toWholesaleOrder(){
        WholesaleOrder wholesaleOrder=new WholesaleOrder();
        wholesaleOrder.setCustomerId(customerId);
        wholesaleOrder.setPayAmount(payAmount);
        wholesaleOrder.setShipMethodId(shipMethodId);
        wholesaleOrder.setShipPrice(shipPrice);
        wholesaleOrder.setTotalWeight(totalWeight);
        wholesaleOrder.setProductAmount(productAmount);
        wholesaleOrder.setVatAmount(vatAmount);
        wholesaleOrder.setProductDischargeAmount(productDischargeAmount);
        wholesaleOrder.setFixFee(fixFee);
        wholesaleOrder.setPayMethod(payMethod);
        wholesaleOrder.setPayTime(payTime);
        wholesaleOrder.setPaymentId(paymentId);
        wholesaleOrder.setPayState(payState);
        wholesaleOrder.setRefundState(refundState);
        wholesaleOrder.setShipState(shipState);
        wholesaleOrder.setToAreaId(toAreaId);
        wholesaleOrder.setCreateTime(createTime);
        wholesaleOrder.setUpdateTime(updateTime);
        wholesaleOrder.setSaiheOrderCode(saiheOrderCode);
        wholesaleOrder.setOrderStatus(orderStatus);
        wholesaleOrder.setOrderType(orderType);
        return wholesaleOrder;
    }

}
