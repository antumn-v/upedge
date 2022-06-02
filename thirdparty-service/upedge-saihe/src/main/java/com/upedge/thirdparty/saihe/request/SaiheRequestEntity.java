package com.upedge.thirdparty.saihe.request;



import javax.xml.bind.annotation.*;

/**
 * Created by cjq on 2018/12/4.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "header","body"
})
@XmlRootElement(name = "soap12:Envelope")
public class SaiheRequestEntity {
    @XmlAttribute(name="xmlns:soap12")
    protected String soap="http://www.w3.org/2003/05/soap-envelope";
    @XmlAttribute(name="xmlns:xsi")
    protected String xsi="http://www.w3.org/2001/XMLSchema-instance";
    @XmlAttribute(name="xmlns:xsd")
    protected String xsd="http://www.w3.org/2001/XMLSchema";




    @XmlElement(required = true,name="soap12:Body")
    protected SaiheRequestBody body;

    @XmlElement(required = true,name="soap:Header")
    protected SaiheRequestHeader header;

    public SaiheRequestBody getBody() {
        return body;
    }

    public void setBody(SaiheRequestBody body) {
        this.body = body;
    }

    public SaiheRequestHeader getHeader() {
        return header;
    }

    public void setHeader(SaiheRequestHeader header) {
        this.header = header;
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
