package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.StoreOrderRefundItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreOrderRefundItemAddRequest{

    /**
    * 
    */
    private Long storeOrderRefundId;
    /**
    * 
    */
    private Long storeOrderItemId;
    /**
    * 
    */
    private Long platOrderRefundId;
    /**
    * 
    */
    private Long storeOrderId;
    /**
    * 
    */
    private Integer refundQuantity;
    /**
    * 
    */
    private BigDecimal refundAmount;
    /**
    * 
    */
    private BigDecimal usdPrice;
    /**
    * 
    */
    private Date createTime;

    public StoreOrderRefundItem toStoreOrderRefundItem(){
        StoreOrderRefundItem storeOrderRefundItem=new StoreOrderRefundItem();
        storeOrderRefundItem.setStoreOrderRefundId(storeOrderRefundId);
        storeOrderRefundItem.setStoreOrderItemId(storeOrderItemId);
        storeOrderRefundItem.setRefundQuantity(refundQuantity);
        storeOrderRefundItem.setRefundAmount(refundAmount);
        return storeOrderRefundItem;
    }

}
