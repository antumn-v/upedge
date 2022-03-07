package com.upedge.oms.modules.stock.vo;

import com.upedge.common.model.product.ProductSaiheInventoryVo;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerProductStockVo {

    /**
     *
     */
    private Long id;
    /**
     *
     */
    private Long customerId;
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
    private Integer stock;
    /**
     *
     */
    private String warehouseCode;
    /**
     * 被锁定的库存
     */
    private Integer lockStock;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date updateTime;
    /**
     *
     */
    private String variantImage;

    private String variantSku;

    private String managerCode;

    private String customerLoginName;

    private String customerName;


   // private AdminSaiheInventory adminSaiheInventory=new AdminSaiheInventory();
    private ProductSaiheInventoryVo productSaiheInventoryVo=new ProductSaiheInventoryVo();
}
