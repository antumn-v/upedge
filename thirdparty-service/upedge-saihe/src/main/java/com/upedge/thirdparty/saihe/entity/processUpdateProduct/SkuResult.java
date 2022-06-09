package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

public class SkuResult {

    private String Sku;

    private String ClientSku;

    @XmlElement(name="Sku")
    public String getSku() {
        return Sku;
    }

    public void setSku(String sku) {
        Sku = sku;
    }

    @XmlElement(name="ClientSku")
    public String getClientSku() {
        return ClientSku;
    }

    public void setClientSku(String clientSku) {
        ClientSku = clientSku;
    }
}
