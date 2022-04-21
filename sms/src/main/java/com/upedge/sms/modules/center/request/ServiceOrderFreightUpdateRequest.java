package com.upedge.sms.modules.center.request;

import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import lombok.Data;

import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class ServiceOrderFreightUpdateRequest{

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

    public ServiceOrderFreight toServiceOrderFreight(Long id){
        ServiceOrderFreight ServiceOrderFreight=new ServiceOrderFreight();
        ServiceOrderFreight.setId(id);
        ServiceOrderFreight.setOrderId(orderId);
        ServiceOrderFreight.setShipType(shipType);
        ServiceOrderFreight.setShipPrice(shipPrice);
        return ServiceOrderFreight;
    }

}
