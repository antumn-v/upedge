package com.upedge.thirdparty.saihe.entity.getPackages;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/5/15.
 */
public class OrderInfoList {

    List<ApiPackageOrderInfo> orderInfoList=new ArrayList<>();

    @XmlElement(name="ApiPackageOrderInfo")
    public List<ApiPackageOrderInfo> getOrderInfoList() {
        return orderInfoList;
    }

    public void setOrderInfoList(List<ApiPackageOrderInfo> orderInfoList) {
        this.orderInfoList = orderInfoList;
    }
}
