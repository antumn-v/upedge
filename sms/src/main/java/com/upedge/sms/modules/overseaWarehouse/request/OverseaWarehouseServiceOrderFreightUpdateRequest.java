package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderFreightUpdateRequest{

    /**
     * 
     */
    private Integer shipType;
    /**
     * 
     */
    private BigDecimal shipPrice;

    public OverseaWarehouseServiceOrderFreight toOverseaWarehouseServiceOrderFreight(Long id){
        OverseaWarehouseServiceOrderFreight overseaWarehouseServiceOrderFreight=new OverseaWarehouseServiceOrderFreight();
        overseaWarehouseServiceOrderFreight.setOrderId(orderId);
        overseaWarehouseServiceOrderFreight.setShipType(shipType);
        overseaWarehouseServiceOrderFreight.setShipPrice(shipPrice);
        return overseaWarehouseServiceOrderFreight;
    }

}
