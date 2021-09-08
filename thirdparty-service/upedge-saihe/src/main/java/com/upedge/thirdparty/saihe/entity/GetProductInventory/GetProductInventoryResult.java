package com.upedge.thirdparty.saihe.entity.GetProductInventory;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jiaqi on 2019/9/9.
 */
public class GetProductInventoryResult {

    String Status;

    String Msg;

    ProductInventoryList ProductInventoryList=new ProductInventoryList();//库存产品集合

    Integer NextToken;

    public GetProductInventoryResult() {
    }

    public GetProductInventoryResult(String Status) {
        this.Status=Status;
    }

    @XmlElement(name="ProductInventoryList")
    public ProductInventoryList getProductInventoryList() {
        return ProductInventoryList;
    }

    public void setProductInventoryList(ProductInventoryList productInventoryList) {
        ProductInventoryList = productInventoryList;
    }

    @XmlElement(name="Msg")
    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
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
}
