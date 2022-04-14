package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
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
    private Long orderId;
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
        overseaWarehouseServiceOrderFreight.setOrderId(orderId);
        overseaWarehouseServiceOrderFreight.setShipType(shipType);
        overseaWarehouseServiceOrderFreight.setShipPrice(shipPrice);
        return overseaWarehouseServiceOrderFreight;
    }

}
