package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.TrackingMoreCarrier;
import lombok.Data;

/**
 * @author author
 */
@Data
public class TrackingMoreCarrierAddRequest{

    /**
    * 
    */
    private String name;
    /**
    * 
    */
    private String phone;
    /**
    * 
    */
    private String homepage;
    /**
    * 
    */
    private String type;
    /**
    * 
    */
    private String picture;
    /**
    * 
    */
    private String nameCn;
    /**
    * 
    */
    private Integer state;

    public TrackingMoreCarrier toTrackingMoreCarrier(){
        TrackingMoreCarrier trackingMoreCarrier=new TrackingMoreCarrier();
        trackingMoreCarrier.setName(name);
        trackingMoreCarrier.setPhone(phone);
        trackingMoreCarrier.setHomepage(homepage);
        trackingMoreCarrier.setType(type);
        trackingMoreCarrier.setPicture(picture);
        trackingMoreCarrier.setNameCn(nameCn);
        trackingMoreCarrier.setState(state);
        return trackingMoreCarrier;
    }

}
