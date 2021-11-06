package com.upedge.oms.modules.pack.request;

import com.upedge.oms.modules.pack.entity.PackageTracking;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class PackageTrackingAddRequest{

    /**
    * 
    */
    private Integer transportId;
    /**
    * 
    */
    private String trackingMoreCode;
    /**
    * 
    */
    private Integer state;
    /**
    * 
    */
    private Date onlineTime;
    /**
    * 
    */
    private Date signedTime;
    /**
    * 
    */
    private Date shipTime;
    /**
    * 
    */
    private String trackStatus;
    /**
    * 
    */
    private String trackInfo;
    /**
    * 
    */
    private Long customerId;
    /**
    * 
    */
    private Integer orderSourceId;
    /**
    * 
    */
    private String orderSourceName;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 
    */
    private String clientOrderCode;
    /**
    * 
    */
    private String destination;

    public PackageTracking toPackageTracking(){
        PackageTracking packageTracking=new PackageTracking();
        packageTracking.setTransportId(transportId);
        packageTracking.setTrackingMoreCode(trackingMoreCode);
        packageTracking.setState(state);
        packageTracking.setOnlineTime(onlineTime);
        packageTracking.setSignedTime(signedTime);
        packageTracking.setShipTime(shipTime);
        packageTracking.setTrackStatus(trackStatus);
        packageTracking.setCustomerId(customerId);
        packageTracking.setOrderSourceId(orderSourceId);
        packageTracking.setOrderSourceName(orderSourceName);
        packageTracking.setUpdateTime(updateTime);
        packageTracking.setClientOrderCode(clientOrderCode);
        packageTracking.setDestination(destination);
        return packageTracking;
    }

}
