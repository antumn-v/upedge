package com.upedge.thirdparty.saihe.entity.cancelOrderInfo;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/7/17.
 */
public class CancelOrderInfoResult {

    public CancelOrderInfoResult(String Status){
        this.Status=Status;
    }

    public CancelOrderInfoResult() {
    }

    String Status="";
    String Msg="";
    Boolean IsSuccess;

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

    @XmlElement(name="IsSuccess")
    public Boolean getSuccess() {
        return IsSuccess;
    }

    public void setSuccess(Boolean success) {
        IsSuccess = success;
    }
}
