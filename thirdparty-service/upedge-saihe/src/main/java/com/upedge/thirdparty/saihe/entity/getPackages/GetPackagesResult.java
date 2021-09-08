package com.upedge.thirdparty.saihe.entity.getPackages;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/5/15.
 */
public class GetPackagesResult {

    public GetPackagesResult(String Status){
        this.Status=Status;
    }

    public GetPackagesResult() {
    }

    //响应状态码
    String Status="";
    //返回消息
    String Msg;
    //包裹列表信息
    ApiPackageList apiPackageList=new ApiPackageList();
    //是否设置分页标识
    boolean IsSetNextToken;
    //分页标识
    Integer NextToken;
    //是否设置包裹信息集合
    boolean IsSetPackages;


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

    @XmlElement(name="ApiPackageList")
    public ApiPackageList getApiPackageList() {
        return apiPackageList;
    }

    public void setApiPackageList(ApiPackageList apiPackageList) {
        this.apiPackageList = apiPackageList;
    }

    @XmlElement(name="IsSetNextToken")
    public boolean isSetNextToken() {
        return IsSetNextToken;
    }

    public void setSetNextToken(boolean setNextToken) {
        IsSetNextToken = setNextToken;
    }

    @XmlElement(name="NextToken")
    public Integer getNextToken() {
        return NextToken;
    }

    public void setNextToken(Integer nextToken) {
        NextToken = nextToken;
    }

    @XmlElement(name="IsSetPackages")
    public boolean isSetPackages() {
        return IsSetPackages;
    }

    public void setSetPackages(boolean setPackages) {
        IsSetPackages = setPackages;
    }
}
