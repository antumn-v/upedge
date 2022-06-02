package com.upedge.thirdparty.saihe.entity.uploadOrder;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class UpLoadOrderV2Response {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    public UpLoadOrderV2Response() {
    }
    public UpLoadOrderV2Response(String msg) {
        this.upLoadOrderV2Result=new UpLoadOrderV2Result(msg);
    }

    UpLoadOrderV2Result upLoadOrderV2Result;

    @XmlElement(name="UpLoadOrderV2Result")
    public UpLoadOrderV2Result getUpLoadOrderV2Result() {
        return upLoadOrderV2Result;
    }

    public void setUpLoadOrderV2Result(UpLoadOrderV2Result upLoadOrderV2Result) {
        this.upLoadOrderV2Result = upLoadOrderV2Result;
    }

}
