package com.upedge.thirdparty.saihe.entity.GetPurchasePutInLogList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2020/2/21.
 */
public class ApiGetInPurchaseDetailResponse {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    public ApiGetInPurchaseDetailResponse() {
    }

    public ApiGetInPurchaseDetailResponse(String Status) {
        this.getPurchasePutInLogListResult=new GetPurchasePutInLogListResult(Status);
    }

    GetPurchasePutInLogListResult getPurchasePutInLogListResult=new GetPurchasePutInLogListResult();

    @XmlElement(name="GetPurchasePutInLogListResult")
    public GetPurchasePutInLogListResult getGetPurchasePutInLogListResult() {
        return getPurchasePutInLogListResult;
    }

    public void setGetPurchasePutInLogListResult(GetPurchasePutInLogListResult getPurchasePutInLogListResult) {
        this.getPurchasePutInLogListResult = getPurchasePutInLogListResult;
    }
}
