package com.upedge.thirdparty.saihe.entity.getWareHouseList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class GetWareHouseListResult {
    public GetWareHouseListResult() {
    }

    public GetWareHouseListResult(String status) {
        Status = status;
    }

    WareHouseList WareHouseList=new WareHouseList();

    String Status;
    String Msg;

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

    @XmlElement(name="WareHouseList")
    public WareHouseList getWareHouseList() {
        return WareHouseList;
    }

    public void setWareHouseList(WareHouseList wareHouseList) {
        WareHouseList = wareHouseList;
    }


}
