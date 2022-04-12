package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderFreightAddRequest{

    /**
    * 
    */
    private Integer shipType;
    /**
    * 
    */
    private BigDecimal shipPrice;

    public OverseaWarehouseServiceOrderFreight toOverseaWarehouseServiceOrderFreight(){
        OverseaWarehouseServiceOrderFreight overseaWarehouseServiceOrderFreight=new OverseaWarehouseServiceOrderFreight();
        overseaWarehouseServiceOrderFreight.setShipType(shipType);
        overseaWarehouseServiceOrderFreight.setShipPrice(shipPrice);
        return overseaWarehouseServiceOrderFreight;
    }

}
