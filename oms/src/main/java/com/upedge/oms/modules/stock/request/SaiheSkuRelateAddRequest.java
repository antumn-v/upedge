package com.upedge.oms.modules.stock.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.stock.entity.SaiheSkuRelate;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class SaiheSkuRelateAddRequest{

    /**
    * 
    */
    private String saiheSku;

    public SaiheSkuRelate toSaiheSkuRelate(){
        SaiheSkuRelate saiheSkuRelate=new SaiheSkuRelate();
        saiheSkuRelate.setSaiheSku(saiheSku);
        return saiheSkuRelate;
    }

}
