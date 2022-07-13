package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class VariantWarehouseStockRecordUpdateRequest{

    /**
     * 
     */
    private Long variantId;
    /**
     * 
     */
    private String warehouseCode;
    /**
     * 
     */
    private Integer changeStock;
    /**
     * 
     */
    private Integer processType;
    /**
     * 
     */
    private Integer originalStock;
    /**
     * 
     */
    private Integer nowStock;
    /**
     * 
     */
    private Long relateId;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private String shelfNum;

    public VariantWarehouseStockRecord toVariantWarehouseStockRecord(Integer id){
        VariantWarehouseStockRecord variantWarehouseStockRecord=new VariantWarehouseStockRecord();
        variantWarehouseStockRecord.setId(id);
        variantWarehouseStockRecord.setVariantId(variantId);
        variantWarehouseStockRecord.setWarehouseCode(warehouseCode);
        variantWarehouseStockRecord.setChangeStock(changeStock);
        variantWarehouseStockRecord.setProcessType(processType);
        variantWarehouseStockRecord.setOriginalStock(originalStock);
        variantWarehouseStockRecord.setNowStock(nowStock);
        variantWarehouseStockRecord.setRelateId(relateId);
        variantWarehouseStockRecord.setCreateTime(createTime);
        variantWarehouseStockRecord.setShelfNum(shelfNum);
        return variantWarehouseStockRecord;
    }

}
