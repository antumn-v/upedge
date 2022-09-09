package com.upedge.common.model.pms.vo;

import lombok.Data;

import java.util.Date;

@Data
public class VariantWarehouseStockModel {

    private Long variantId;

    private String variantSku;

    private String barcode;
    /**
     * 仓库代码
     */
    private String warehouseCode;
    /**
     * 出库比例
     */
    private Integer stockScale;
    /**
     * 安全库存
     */
    private Integer safeStock;
    /**
     * 可用库存
     */
    private Integer availableStock;
    /**
     * 锁定库存
     */
    private Integer lockStock;
    /**
     * 采购库存
     */
    private Integer purchaseStock;
    /**
     * 备注
     */
    private String remark;
    /**
     *
     */
    private String shelfNum;

    private Date updateTime;
}
