package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderProfit;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class OrderProfitUpdateRequest{

    /**
     * 
     */
    private Long orderId;
    /**
     * 
     */
    private BigDecimal storeOrderItemAmount;
    /**
     * 
     */
    private BigDecimal storeOrderOtherFee;
    /**
     * 
     */
    private BigDecimal storeOrderRefundAmount;
    /**
     * 
     */
    private BigDecimal orderRefundAmount;
    /**
     * 
     */
    private BigDecimal orderPayAmount;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;

    public OrderProfit toOrderProfit(Long orderId){
        OrderProfit orderProfit=new OrderProfit();
        orderProfit.setOrderId(orderId);
        orderProfit.setStoreOrderItemAmount(storeOrderItemAmount);
        orderProfit.setStoreOrderOtherFee(storeOrderOtherFee);
        orderProfit.setStoreOrderRefundAmount(storeOrderRefundAmount);
        orderProfit.setOrderRefundAmount(orderRefundAmount);
        orderProfit.setOrderPayAmount(orderPayAmount);
        orderProfit.setCreateTime(createTime);
        orderProfit.setUpdateTime(updateTime);
        return orderProfit;
    }

}
