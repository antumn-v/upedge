package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.SaiheTransport;
import lombok.Data;

/**
 * @author author
 */
@Data
public class SaiheTransportUpdateRequest{

    /**
     * 
     */
    private String trackingMoreCode;

    public SaiheTransport toSaiheTransport(Integer id){
        SaiheTransport saiheTransport=new SaiheTransport();
        saiheTransport.setId(id);
        saiheTransport.setTrackingMoreCode(trackingMoreCode);
        return saiheTransport;
    }

}
