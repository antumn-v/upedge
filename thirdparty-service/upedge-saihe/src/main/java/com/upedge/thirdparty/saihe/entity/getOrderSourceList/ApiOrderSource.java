package com.upedge.thirdparty.saihe.entity.getOrderSourceList;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by cjq on 2019/4/9.
 */
public class ApiOrderSource {

    Integer ID;//来源渠道ID
    String OrderSourceName;//来源渠道名称
    Integer OrderSourceType;  //来源渠道类型（枚举）

    @XmlElement(name="ID")
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @XmlElement(name="OrderSourceName")
    public String getOrderSourceName() {
        return OrderSourceName;
    }

    public void setOrderSourceName(String orderSourceName) {
        OrderSourceName = orderSourceName;
    }

    @XmlElement(name="OrderSourceType")
    public Integer getOrderSourceType() {
        return OrderSourceType;
    }

    public void setOrderSourceType(Integer orderSourceType) {
        OrderSourceType = orderSourceType;
    }
}
