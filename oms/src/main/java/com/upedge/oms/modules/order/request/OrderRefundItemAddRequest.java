package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderRefundItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class OrderRefundItemAddRequest{

    /**
    * 
    */
    private Long orderId;
    /**
    * 
    */
    private Integer quantity;
    /**
    * 
    */
    private BigDecimal price;
    /**
    * 
    */
    private Long refundId;
    /**
    * 
    */
    private Long orderItemId;
    /**
    * 
    */
    private String variantSku;
    /**
    * 
    */
    private String variantImage;

    public OrderRefundItem toOrderRefundItem(){
        OrderRefundItem orderRefundItem=new OrderRefundItem();
        orderRefundItem.setOrderId(orderId);
        orderRefundItem.setQuantity(quantity);
        orderRefundItem.setPrice(price);
        orderRefundItem.setRefundId(refundId);
        orderRefundItem.setOrderItemId(orderItemId);
        orderRefundItem.setVariantSku(variantSku);
        orderRefundItem.setVariantImage(variantImage);
        return orderRefundItem;
    }

}
