package com.upedge.thirdparty.saihe.entity.getTransportList;

import javax.xml.bind.annotation.XmlElement;

public class ApiTransport {
    //运输方式ID
    Integer ID;
    //运输商
    String CarrierName;
    //运输方式中文名
    String TransportName;
    //运输方式英文名
    String TransportNameEn;
    //是否挂号
    Boolean IsRegistered;
    @XmlElement(name="ID")
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @XmlElement(name="CarrierName")
    public String getCarrierName() {
        return CarrierName;
    }

    public void setCarrierName(String carrierName) {
        CarrierName = carrierName;
    }

    @XmlElement(name="TransportName")
    public String getTransportName() {
        return TransportName;
    }

    public void setTransportName(String transportName) {
        TransportName = transportName;
    }

    @XmlElement(name="TransportNameEn")
    public String getTransportNameEn() {
        return TransportNameEn;
    }

    public void setTransportNameEn(String transportNameEn) {
        TransportNameEn = transportNameEn;
    }

    @XmlElement(name="IsRegistered")
    public Boolean getRegistered() {
        return IsRegistered;
    }

    public void setRegistered(Boolean registered) {
        IsRegistered = registered;
    }
}
