package com.upedge.thirdparty.saihe.entity.getWareHouseList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/1/11.
 */
public class WareHouseList {

    List<ApiWareHouse> apiWareHouse=new ArrayList<>();

    @XmlElement(name="ApiWareHouse")
    public List<ApiWareHouse> getApiWareHouse() {
        return apiWareHouse;
    }

    public void setApiWareHouse(List<ApiWareHouse> apiWareHouse) {
        this.apiWareHouse = apiWareHouse;
    }
}
