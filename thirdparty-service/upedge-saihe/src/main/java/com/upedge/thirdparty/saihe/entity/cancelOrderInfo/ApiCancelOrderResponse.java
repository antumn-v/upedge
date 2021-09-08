package com.upedge.thirdparty.saihe.entity.cancelOrderInfo;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/6/10.
 */
public class ApiCancelOrderResponse {
     @XmlAttribute(name="xmlns")
     protected String xmlns="http://tempuri.org/";

     CancelOrderInfoResult cancelOrderResult=new CancelOrderInfoResult();

     @XmlElement(name="CancelOrderInfoResult")
     public CancelOrderInfoResult getCancelOrderResult() {
          return cancelOrderResult;
     }

     public void setCancelOrderResult(CancelOrderInfoResult cancelOrderResult) {
          this.cancelOrderResult = cancelOrderResult;
     }

     public ApiCancelOrderResponse() {
     }

     public ApiCancelOrderResponse(String Status) {
          this.cancelOrderResult=new CancelOrderInfoResult(Status);
     }


}
