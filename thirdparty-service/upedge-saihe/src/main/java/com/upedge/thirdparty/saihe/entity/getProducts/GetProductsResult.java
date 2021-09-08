package com.upedge.thirdparty.saihe.entity.getProducts;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by jiaqi on 2019/9/2.
 */
public class GetProductsResult {

    public GetProductsResult(String Status){
        this.Status=Status;
    }

    public GetProductsResult() {
    }

    String Status;

    //返回消息
    String Msg;

    //分页标识
    Integer NextToken;

    //是否设置分页标识
    boolean IsSetNextToken;

    //是否设置产品信息
    boolean IsSetProducts;

    //包裹列表信息
    ProductInfoList productInfoList=new ProductInfoList();

    @XmlElement(name="Status")
    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @XmlElement(name="Msg")
    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }

    @XmlElement(name="IsSetNextToken")
    public boolean isSetNextToken() {
        return IsSetNextToken;
    }

    public void setSetNextToken(boolean setNextToken) {
        IsSetNextToken = setNextToken;
    }

    @XmlElement(name="IsSetProducts")
    public boolean isSetProducts() {
        return IsSetProducts;
    }

    public void setSetProducts(boolean setProducts) {
        IsSetProducts = setProducts;
    }

    @XmlElement(name="ProductInfoList")
    public ProductInfoList getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(ProductInfoList productInfoList) {
        this.productInfoList = productInfoList;
    }
}
