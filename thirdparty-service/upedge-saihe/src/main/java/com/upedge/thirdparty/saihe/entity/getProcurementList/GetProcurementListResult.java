package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2020/7/21.
 */
public class GetProcurementListResult {

    String Status;

    Integer NextToken;

    String Msg;

    ProcurementList procurementList=new ProcurementList();

    public GetProcurementListResult() {

    }

    public GetProcurementListResult(String status) {
        this.Status=Status;
    }

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }

    @XmlElement(name="Msg")
    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @XmlElement(name="ProcurementList")
    public ProcurementList getProcurementList() {
        return procurementList;
    }

    public void setProcurementList(ProcurementList procurementList) {
        this.procurementList = procurementList;
    }
}
