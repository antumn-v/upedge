package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/4/4.
 */
public class ApiUploadResult {

    Boolean IsSuccess;
    String OperateMessage;

    @XmlElement(name="OperateMessage")
    public String getOperateMessage() {
        return OperateMessage;
    }

    public void setOperateMessage(String operateMessage) {
        OperateMessage = operateMessage;
    }

    @XmlElement(name="IsSuccess")
    public Boolean getSuccess() {
        return IsSuccess;
    }

    public void setSuccess(Boolean success) {
        IsSuccess = success;
    }
}
