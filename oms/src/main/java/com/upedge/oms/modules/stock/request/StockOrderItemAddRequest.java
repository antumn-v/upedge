package com.upedge.oms.modules.stock.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.StockOrderItem;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class StockOrderItemAddRequest{

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
    /**
    * 已入库数量
    */
    private Integer inboundQuantity;
    /**
    * 采购单号
    */
    private String purchaseNo;

    public StockOrderItem toStockOrderItem(){
        StockOrderItem stockOrderItem=new StockOrderItem();
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
        stockOrderItem.setInboundQuantity(inboundQuantity);
        stockOrderItem.setPurchaseNo(purchaseNo);
        return stockOrderItem;
    }

}
