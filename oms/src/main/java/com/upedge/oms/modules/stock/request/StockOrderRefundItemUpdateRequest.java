package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.StockOrderRefundItem;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StockOrderRefundItemUpdateRequest{

    /**
     * 退款id
     */
    private Long stockRefundId;
    /**
     * 变体数量
     */
    private Integer quantity;
    /**
     * 单个价格
     */
    private BigDecimal price;
    /**
     * 关联退款订单项id
     */
    private Long stockOrderItemId;
    /**
     * 
     */
    private Long productId;
    /**
     * 
     */
    private Long variantId;
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

    public StockOrderRefundItem toStockOrderRefundItem(Long id){
        StockOrderRefundItem stockOrderRefundItem=new StockOrderRefundItem();
        stockOrderRefundItem.setId(id);
        stockOrderRefundItem.setStockRefundId(stockRefundId);
        stockOrderRefundItem.setQuantity(quantity);
        stockOrderRefundItem.setPrice(price);
        stockOrderRefundItem.setStockOrderItemId(stockOrderItemId);
        stockOrderRefundItem.setProductId(productId);
        stockOrderRefundItem.setVariantId(variantId);
        stockOrderRefundItem.setProductTitle(productTitle);
        stockOrderRefundItem.setVariantName(variantName);
        stockOrderRefundItem.setVariantSku(variantSku);
        stockOrderRefundItem.setVariantImage(variantImage);
        return stockOrderRefundItem;
    }

}
