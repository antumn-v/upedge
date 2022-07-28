package com.upedge.pms.modules.purchase.request;

import lombok.Data;
/**
 * @author gx
 */
@Data
public class VariantWarehouseStockUpdateRequest{

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

}
