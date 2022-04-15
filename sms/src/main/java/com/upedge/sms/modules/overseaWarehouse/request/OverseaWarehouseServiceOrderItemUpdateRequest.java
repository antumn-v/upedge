package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderItemUpdateRequest{

    /**
     * 
     */
    private Long orderId;
    /**
     * 
     */
    private Long variantId;
    /**
     * 
     */
    private Long productId;
    /**
     * 
     */
    private String variantName;
    /**
     * 
     */
    private String variantSku;
    /**
     * 
     */
    private String variantImage;
    /**
     * 
     */
    private String productTitle;
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
    private String warehouseSku;

    public OverseaWarehouseServiceOrderItem toOverseaWarehouseServiceOrderItem(Long id){
        OverseaWarehouseServiceOrderItem overseaWarehouseServiceOrderItem=new OverseaWarehouseServiceOrderItem();
        overseaWarehouseServiceOrderItem.setId(id);
        overseaWarehouseServiceOrderItem.setOrderId(orderId);
        overseaWarehouseServiceOrderItem.setVariantId(variantId);
        overseaWarehouseServiceOrderItem.setProductId(productId);
        overseaWarehouseServiceOrderItem.setVariantName(variantName);
        overseaWarehouseServiceOrderItem.setVariantSku(variantSku);
        overseaWarehouseServiceOrderItem.setVariantImage(variantImage);
        overseaWarehouseServiceOrderItem.setProductTitle(productTitle);
        overseaWarehouseServiceOrderItem.setQuantity(quantity);
        overseaWarehouseServiceOrderItem.setPrice(price);
        overseaWarehouseServiceOrderItem.setWarehouseSku(warehouseSku);
        return overseaWarehouseServiceOrderItem;
    }

}
