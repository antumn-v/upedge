package com.upedge.pms.modules.product.request;

import com.upedge.pms.modules.product.entity.VariantSkuUpdateLog;
import lombok.Data;

import java.util.Date;
/**
 * @author gx
 */
@Data
public class VariantSkuUpdateLogUpdateRequest{

    /**
     * 
     */
    private String variantSku;
    /**
     * 
     */
    private Date updateTime;

    public VariantSkuUpdateLog toVariantSkuUpdateLog(Long variantId){
        VariantSkuUpdateLog variantSkuUpdateLog=new VariantSkuUpdateLog();
        variantSkuUpdateLog.setVariantId(variantId);
        variantSkuUpdateLog.setVariantSku(variantSku);
        variantSkuUpdateLog.setUpdateTime(updateTime);
        return variantSkuUpdateLog;
    }

}
