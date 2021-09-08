package com.upedge.thirdparty.saihe.entity.getWareHouseList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/1/11.
 */
public class ApiWareHouse {

    //发货仓库id
    Integer ID;
    //发货仓库名称
    String WareHouseName;
    //发货仓库类型（枚举）
    Integer WareHouseType;

    @XmlElement(name="ID")
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @XmlElement(name="WareHouseName")
    public String getWareHouseName() {
        return WareHouseName;
    }

    public void setWareHouseName(String wareHouseName) {
        WareHouseName = wareHouseName;
    }

    @XmlElement(name="WareHouseType")
    public Integer getWareHouseType() {
        return WareHouseType;
    }

    public void setWareHouseType(Integer wareHouseType) {
        WareHouseType = wareHouseType;
    }
}
