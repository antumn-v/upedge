package com.upedge.thirdparty.saihe.entity.GetProductInventory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by guoxing on 2019/9/9.
 */
public class ApiGetProductInventoryRequest {

    @XmlAttribute(name="xmlns")
    protected String xmlns="http://tempuri.org/";

    InventoryRequest inventoryRequest;

    @XmlElement(name="request")
    public InventoryRequest getInventoryRequest() {
        return inventoryRequest;
    }

    public void setInventoryRequest(InventoryRequest inventoryRequest) {
        this.inventoryRequest = inventoryRequest;
    }

    /*public static void main(String[] args) {

        RequestEntity1 requestEntity=new RequestEntity1();
        RequestBody1 requestBody=new RequestBody1();


        ApiGetProductInventoryRequest apiGetProductInventoryRequest=new ApiGetProductInventoryRequest();
        InventoryRequest inventoryRequest=new InventoryRequest();
        inventoryRequest.setUserName("upedgeDUIJIE");
        inventoryRequest.setPassword("LSJDKJHASDNBS");
        inventoryRequest.setCustomerID(1555);
        inventoryRequest.setWarehouseID(162);
        inventoryRequest.setClientSKU("4190876690284");

        apiGetProductInventoryRequest.setInventoryRequest(inventoryRequest);
        requestBody.setApiGetProductInventoryRequest(apiGetProductInventoryRequest);
        requestEntity.setBody(requestBody);

        String xmlStr2 = XmlAndJavaObjectConvert.convertToXml(requestEntity);
        System.out.println(xmlStr2);

    }*/

}
