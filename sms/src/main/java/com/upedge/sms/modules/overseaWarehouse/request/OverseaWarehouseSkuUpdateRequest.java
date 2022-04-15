package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseSku;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class OverseaWarehouseSkuUpdateRequest{

    /**
     * 
     */
    private String variantSku;
    /**
     * 
     */
    private String warehouseSkuId;
    /**
     * 
     */
    private String warehouseSkuCode;
    /**
     * 
     */
    private Date createTime;

    public OverseaWarehouseSku toOverseaWarehouseSku(Long variantId){
        OverseaWarehouseSku overseaWarehouseSku=new OverseaWarehouseSku();
        overseaWarehouseSku.setVariantId(variantId);
        overseaWarehouseSku.setVariantSku(variantSku);
        overseaWarehouseSku.setWarehouseSkuId(warehouseSkuId);
        overseaWarehouseSku.setWarehouseSkuCode(warehouseSkuCode);
        overseaWarehouseSku.setCreateTime(createTime);
        return overseaWarehouseSku;
    }

}
