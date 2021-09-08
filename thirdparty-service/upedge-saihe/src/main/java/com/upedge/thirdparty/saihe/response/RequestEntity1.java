package com.upedge.thirdparty.saihe.response;


import javax.xml.bind.annotation.*;

/**
 * Created by cjq on 2018/12/4.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "body"
})
@XmlRootElement(name = "soap:Envelope")
public class RequestEntity1 {
    @XmlAttribute(name="xmlns:soap")
    protected String soap="http://schemas.xmlsoap.org/soap/envelope/";
    @XmlAttribute(name="xmlns:xsi")
    protected String xsi="http://www.w3.org/2001/XMLSchema-instance";
    @XmlAttribute(name="xmlns:xsd")
    protected String xsd="http://www.w3.org/2001/XMLSchema";




    @XmlElement(required = true,name="soap:Body")
    protected RequestBody1 body;


    public RequestBody1 getBody() {
        return body;
    }

    public void setBody(RequestBody1 body) {
        this.body = body;
    }

    public String getXsi() {
        return xsi;
    }

    public void setXsi(String xsi) {
        this.xsi = xsi;
    }

    public String getXsd() {
        return xsd;
    }

    public void setXsd(String xsd) {
        this.xsd = xsd;
    }

    public String getSoap() {
        return soap;
    }

    public void setSoap(String soap) {
        this.soap = soap;
    }
}
