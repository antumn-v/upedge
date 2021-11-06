package com.upedge.thirdparty.trackingmore.entity;

import java.util.Date;

/**
 * Created by cjq on 2019/5/8.
 */
public class OriginInfo {

    Date ItemReceived;
    String ItemDispatched;
    String DepartfromAirport;
    String ArrivalfromAbroad;
    String CustomsClearance;
    String DestinationArrived;
    String weblink;
    String phone;
    String carrier_code;

    String trackinfo;
//    List<Trackinfo> trackinfo=new ArrayList<>();

    public String getTrackinfo() {
        return trackinfo;
    }

    public void setTrackinfo(String trackinfo) {
        this.trackinfo = trackinfo;
    }

    public Date getItemReceived() {
        return ItemReceived;
    }

    public void setItemReceived(Date itemReceived) {
        ItemReceived = itemReceived;
    }

    public String getItemDispatched() {
        return ItemDispatched;
    }

    public void setItemDispatched(String itemDispatched) {
        ItemDispatched = itemDispatched;
    }

    public String getDepartfromAirport() {
        return DepartfromAirport;
    }

    public void setDepartfromAirport(String departfromAirport) {
        DepartfromAirport = departfromAirport;
    }

    public String getArrivalfromAbroad() {
        return ArrivalfromAbroad;
    }

    public void setArrivalfromAbroad(String arrivalfromAbroad) {
        ArrivalfromAbroad = arrivalfromAbroad;
    }

    public String getCustomsClearance() {
        return CustomsClearance;
    }

    public void setCustomsClearance(String customsClearance) {
        CustomsClearance = customsClearance;
    }

    public String getDestinationArrived() {
        return DestinationArrived;
    }

    public void setDestinationArrived(String destinationArrived) {
        DestinationArrived = destinationArrived;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCarrier_code() {
        return carrier_code;
    }

    public void setCarrier_code(String carrier_code) {
        this.carrier_code = carrier_code;
    }

//    public List<Trackinfo> getTrackinfo() {
//        return trackinfo;
//    }
//
//    public void setTrackinfo(List<Trackinfo> trackinfo) {
//        this.trackinfo = trackinfo;
//    }
}
