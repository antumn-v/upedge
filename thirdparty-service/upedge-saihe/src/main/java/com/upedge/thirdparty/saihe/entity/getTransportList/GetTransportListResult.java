package com.upedge.thirdparty.saihe.entity.getTransportList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class GetTransportListResult {


    String Status;
    String Msg;
    TransportList transportList=new TransportList();


    @XmlElement(name="Msg")
    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public GetTransportListResult() {
    }

    public GetTransportListResult(String status) {
        Status = status;
    }

    @XmlElement(name="TransportList")
    public TransportList getTransportList() {
        return transportList;

    }

    public void setTransportList(TransportList transportList) {
        this.transportList = transportList;
    }
}
