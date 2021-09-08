package com.upedge.thirdparty.saihe.entity.uploadOrder;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by cjq on 2019/1/15.
 */
public class OrderItemList {
    //订单产品列表
    List<ApiUploadOrderList> OrderItemList;


    @XmlElement(name="ApiUploadOrderList")
    public List<ApiUploadOrderList> getOrderItemList() {
        return OrderItemList;
    }

    public void setOrderItemList(List<ApiUploadOrderList> orderItemList) {
        OrderItemList = orderItemList;
    }

}
