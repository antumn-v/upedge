package com.upedge.sms.modules.wholesale.request;

import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WholesaleOrderUpdateRequest{


    private String trackingCode;

    public WholesaleOrder toWholesaleOrder(Long id){
        WholesaleOrder wholesaleOrder=new WholesaleOrder();
        wholesaleOrder.setId(id);
        wholesaleOrder.setTrackingCode(trackingCode);
        return wholesaleOrder;
    }

}
