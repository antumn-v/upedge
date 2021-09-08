package com.upedge.thirdparty.saihe.entity.GetProductInventory;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaqi on 2019/9/9.
 */
public class ProductInventoryList {

    List<ApiProductInventory> ProductInventoryList=new ArrayList<>();

    @XmlElement(name="ApiProductInventory")
    public List<ApiProductInventory> getProductInventoryList() {
        return ProductInventoryList;
    }

    public void setProductInventoryList(List<ApiProductInventory> productInventoryList) {
        ProductInventoryList = productInventoryList;
    }

}
