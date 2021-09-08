package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/23.
 */
public class Prequest {

     String UserName;
     String Password;
     Integer CustomerID;
     ImportProductList ImportProductList;

    @XmlElement(name="UserName")
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    @XmlElement(name="Password")
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @XmlElement(name="CustomerID")
    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }



    @XmlElement(name="ImportProductList")
    public ImportProductList getImportProductList() {
        return ImportProductList;
    }

    public void setImportProductList(ImportProductList importProductList) {
        ImportProductList = importProductList;
    }
}
