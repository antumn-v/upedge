package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.SaiheTransport;
import lombok.Data;

/**
 * @author author
 */
@Data
public class SaiheTransportAddRequest{

    /**
    * 
    */
    private String carrierName;
    /**
    * 
    */
    private String transportName;
    /**
    * 
    */
    private String transportNameEn;
    /**
    * 
    */
    private Boolean isRegistered;
    /**
    * 
    */
    private String trackingMoreCode;
    /**
    * 17track运输商编码
    */
    private String fcCode;

    public SaiheTransport toSaiheTransport(){
        SaiheTransport saiheTransport=new SaiheTransport();
        saiheTransport.setCarrierName(carrierName);
        saiheTransport.setTransportName(transportName);
        saiheTransport.setTransportNameEn(transportNameEn);
        saiheTransport.setIsRegistered(isRegistered);
        saiheTransport.setTrackingMoreCode(trackingMoreCode);
        saiheTransport.setFcCode(fcCode);
        return saiheTransport;
    }

}
