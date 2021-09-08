package com.upedge.thirdparty.saihe.entity.getOrderByCode;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/1/14.
 */
public class OrderInfoList {

    List<ApiOrderInfo> OrderInfoList=new ArrayList<>();

    @XmlElement(name="ApiOrderInfo")
    public List<ApiOrderInfo> getOrderInfoList() {
        return OrderInfoList;
    }

    public void setOrderInfoList(List<ApiOrderInfo> orderInfoList) {
        OrderInfoList = orderInfoList;
    }

}
