package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoxing on 2020/7/21.
 */
public class ProcurementList {

    List<ApiProcurementInfo> ProductInventoryList=new ArrayList<>();

    @XmlElement(name="ApiProcurementInfo")
    public List<ApiProcurementInfo> getProductInventoryList() {
        return ProductInventoryList;
    }

    public void setProductInventoryList(List<ApiProcurementInfo> productInventoryList) {
        ProductInventoryList = productInventoryList;
    }
}
