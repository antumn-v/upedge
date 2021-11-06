package com.upedge.oms.modules.order.request;

import com.upedge.oms.modules.order.entity.StoreOrderItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StoreOrderItemAddRequest{

    /**
    * 
    */
    private String platOrderId;
    /**
    * 
    */
    private String platOrderItemId;
    /**
    * 
    */
    private Long storeOrderId;
    /**
    * 
    */
    private Long storeVariantId;
    /**
    * 
    */
    private Long storeProductId;
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
    private String storeVariantName;
    /**
    * 
    */
    private String storeVariantSku;
    /**
    * 
    */
    private String storeVariantImage;
    /**
    * 
    */
    private String storeProductTitle;
    /**
    * 0=正常 1=已生成订单
    */
    private Integer state;

    public StoreOrderItem toStoreOrderItem(){
        StoreOrderItem storeOrderItem=new StoreOrderItem();
        storeOrderItem.setPlatOrderId(platOrderId);
        storeOrderItem.setPlatOrderItemId(platOrderItemId);
        storeOrderItem.setStoreOrderId(storeOrderId);
        storeOrderItem.setStoreVariantId(storeVariantId);
        storeOrderItem.setStoreProductId(storeProductId);
        storeOrderItem.setQuantity(quantity);
        storeOrderItem.setPrice(price);
        storeOrderItem.setStoreVariantName(storeVariantName);
        storeOrderItem.setStoreVariantSku(storeVariantSku);
        storeOrderItem.setStoreVariantImage(storeVariantImage);
        storeOrderItem.setStoreProductTitle(storeProductTitle);
        storeOrderItem.setState(state);
        return storeOrderItem;
    }

}
