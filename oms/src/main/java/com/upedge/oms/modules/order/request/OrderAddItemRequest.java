package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderItem;
import lombok.Data;

@Data
public class OrderAddItemRequest {

    private Long orderId;

    private Long storeVariantId;

    private String title;

    private String sku;

    private String image;

    private Integer quantity;

    public OrderItem toOrderItem(Long id){
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setStoreVariantName(title);
        orderItem.setStoreVariantSku(sku);
        orderItem.setStoreVariantId(storeVariantId);
        orderItem.setStoreProductTitle(title);
        orderItem.setStoreVariantImage(image);
        return orderItem;
    }
}
