package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.OrderItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class OrderItemAddRequest{

    /**
    * 
    */
    private Long orderId;
    /**
    * 
    */
    private Long storeOrderItemId;
    /**
    * 
    */
    private Long storeVariantId;
    /**
    * 
    */
    private Long adminVariantId;
    /**
    * 
    */
    private Long adminProductId;
    /**
    * 
    */
    private String storeVariantName;
    /**
    * 
    */
    private String storeVariantSku;
    /**
    * 
    */
    private String adminVariantImage;
    /**
    * 
    */
    private String storeProductTitle;
    /**
    * 
    */
    private BigDecimal usdPrice;
    /**
    * 
    */
    private Integer quantity;
    /**
    * 抵扣数量
    */
    private Integer dischargeQuantity;

    public OrderItem toOrderItem(){
        OrderItem orderItem=new OrderItem();
        orderItem.setOrderId(orderId);
        orderItem.setStoreOrderItemId(storeOrderItemId);
        orderItem.setStoreVariantId(storeVariantId);
        orderItem.setAdminVariantId(adminVariantId);
        orderItem.setAdminProductId(adminProductId);
        orderItem.setStoreVariantName(storeVariantName);
        orderItem.setStoreVariantSku(storeVariantSku);
        orderItem.setAdminVariantImage(adminVariantImage);
        orderItem.setStoreProductTitle(storeProductTitle);
        orderItem.setUsdPrice(usdPrice);
        orderItem.setQuantity(quantity);
        orderItem.setDischargeQuantity(dischargeQuantity);
        return orderItem;
    }

}
