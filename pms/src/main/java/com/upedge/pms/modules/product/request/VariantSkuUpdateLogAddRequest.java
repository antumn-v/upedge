package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.VariantSkuUpdateLog;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class VariantSkuUpdateLogAddRequest{

    /**
    * 
    */
    private String variantSku;
    /**
    * 
    */
    private Date updateTime;

    public VariantSkuUpdateLog toVariantSkuUpdateLog(){
        VariantSkuUpdateLog variantSkuUpdateLog=new VariantSkuUpdateLog();
        variantSkuUpdateLog.setVariantSku(variantSku);
        variantSkuUpdateLog.setUpdateTime(updateTime);
        return variantSkuUpdateLog;
    }

}
