package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderRefundItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class OrderRefundItemUpdateRequest{

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

    public OrderRefundItem toOrderRefundItem(Integer id){
        OrderRefundItem orderRefundItem=new OrderRefundItem();
        orderRefundItem.setId(id);
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
