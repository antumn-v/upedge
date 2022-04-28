package com.upedge.thirdparty.saihe.entity.createProcurement;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2019/9/2.
 */
public class CreateProcurementResult {

    String Status;

    String Msg;

    ProcurementDetailList ProcurementDetailList=new ProcurementDetailList();//采购单结果实体

    String OrderCode;//备库单号

    public CreateProcurementResult() {
    }

    public CreateProcurementResult(String Status) {
        this.Status=Status;
    }

    @XmlElement(name="ProcurementDetailList")
    public ProcurementDetailList getProcurementDetailList() {
        return ProcurementDetailList;
    }

    public void setProcurementDetailList(ProcurementDetailList procurementDetailList) {
        ProcurementDetailList = procurementDetailList;
    }


    @XmlElement(name="OrderCode")
    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

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
}
