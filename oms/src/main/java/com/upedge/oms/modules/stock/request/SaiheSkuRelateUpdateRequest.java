package com.upedge.oms.modules.stock.request;

import com.upedge.oms.modules.stock.entity.SaiheSkuRelate;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class SaiheSkuRelateUpdateRequest{

    /**
     * 
     */
    private String saiheSku;

    public SaiheSkuRelate toSaiheSkuRelate(String variantSku){
        SaiheSkuRelate saiheSkuRelate=new SaiheSkuRelate();
        saiheSkuRelate.setVariantSku(variantSku);
        saiheSkuRelate.setSaiheSku(saiheSku);
        return saiheSkuRelate;
    }

}
