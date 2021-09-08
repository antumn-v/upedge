package com.upedge.thirdparty.saihe.entity.uploadOrder;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/16.
 */
public class UpLoadOrderV2Result {


    //响应状态码
    String Status;

    String Msg;

    String OrderCode;

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @XmlElement(name="Msg")
    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public UpLoadOrderV2Result() {
    }

    public UpLoadOrderV2Result(String Status) {
        this.Status=Status;
    }

    @XmlElement(name="OrderCode")
    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }
}
