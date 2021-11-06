package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.StockOrderItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StockOrderItemUpdateRequest{

    /**
     * 产品ID
     */
    private Long productId;
    /**
     * 变体ID
     */
    private Long variantId;
    /**
     * 订单ID
     */
    private Long orderId;
    /**
     * 购物车ID
     */
    private Long cartId;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 
     */
    private String productTitle;
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

    public StockOrderItem toStockOrderItem(Long id){
        StockOrderItem stockOrderItem=new StockOrderItem();
        stockOrderItem.setId(id);
        stockOrderItem.setProductId(productId);
        stockOrderItem.setVariantId(variantId);
        stockOrderItem.setOrderId(orderId);
        stockOrderItem.setCartId(cartId);
        stockOrderItem.setPrice(price);
        stockOrderItem.setQuantity(quantity);
        stockOrderItem.setProductTitle(productTitle);
        stockOrderItem.setVariantName(variantName);
        stockOrderItem.setVariantSku(variantSku);
        stockOrderItem.setVariantImage(variantImage);
        return stockOrderItem;
    }

}
