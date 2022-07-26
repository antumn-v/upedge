package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.VariantStockExImRecord;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
/**
 * @author gx
 */
@Data
public class VariantStockExImRecordUpdateRequest{

    private Long relateId;

    /**
     * 
     */
    @NotNull
    private String variantSku;
    /**
     * 
     */
    @NotNull
    private String warehouseCode;
    /**
     * 
     */
    @NotNull
    private String trackingCode;
    /**
     * 
     */
    @NotNull
    private Integer quantity;

    private Integer processType;


    public VariantStockExImRecord toVariantStockExImRecord(Integer operateType){
        VariantStockExImRecord variantStockExImRecord=new VariantStockExImRecord();
        variantStockExImRecord.setVariantSku(variantSku);
        variantStockExImRecord.setWarehouseCode(warehouseCode);
        variantStockExImRecord.setTrackingCode(trackingCode);
        variantStockExImRecord.setQuantity(quantity);
        variantStockExImRecord.setOperateType(operateType);
        variantStockExImRecord.setCreateTime(new Date());
        return variantStockExImRecord;
    }

}
