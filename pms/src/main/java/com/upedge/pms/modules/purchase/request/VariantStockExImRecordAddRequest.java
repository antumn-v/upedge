package com.upedge.pms.modules.purchase.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.VariantStockExImRecord;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class VariantStockExImRecordAddRequest{

    /**
    * 
    */
    private Long variantId;
    /**
    * 
    */
    private String variantSku;
    /**
    * 
    */
    private String warehouseCode;
    /**
    * 
    */
    private String trackingCode;
    /**
    * 
    */
    private Integer quantity;
    /**
    * 
    */
    private Long operatorId;
    /**
    * 1=入库  0=出库
    */
    private Integer operateType;
    /**
    * 
    */
    private Date createTime;

    public VariantStockExImRecord toVariantStockExImRecord(){
        VariantStockExImRecord variantStockExImRecord=new VariantStockExImRecord();
        variantStockExImRecord.setVariantId(variantId);
        variantStockExImRecord.setVariantSku(variantSku);
        variantStockExImRecord.setWarehouseCode(warehouseCode);
        variantStockExImRecord.setTrackingCode(trackingCode);
        variantStockExImRecord.setQuantity(quantity);
        variantStockExImRecord.setOperatorId(operatorId);
        variantStockExImRecord.setOperateType(operateType);
        variantStockExImRecord.setCreateTime(createTime);
        return variantStockExImRecord;
    }

}
