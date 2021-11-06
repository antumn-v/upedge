package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.PaypalCarriers;
import lombok.Data;

/**
 * @author author
 */
@Data
public class PaypalCarriersAddRequest{

    /**
    * 
    */
    private String carrier;
    /**
    * 
    */
    private String countryCode;
    /**
    * 
    */
    private String name;

    public PaypalCarriers toPaypalCarriers(){
        PaypalCarriers paypalCarriers=new PaypalCarriers();
        paypalCarriers.setCarrier(carrier);
        paypalCarriers.setCountryCode(countryCode);
        paypalCarriers.setName(name);
        return paypalCarriers;
    }

}
