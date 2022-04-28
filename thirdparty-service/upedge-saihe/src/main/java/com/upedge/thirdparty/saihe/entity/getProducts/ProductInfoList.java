package com.upedge.thirdparty.saihe.entity.getProducts;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoxing on 2019/9/2.
 */
public class ProductInfoList {

    List<ApiProductInfo> ProductInfoList=new ArrayList<>();

    @XmlElement(name="ApiProductInfo")
    public List<ApiProductInfo> getProductInfoList() {
        return ProductInfoList;
    }

    public void setProductInfoList(List<ApiProductInfo> productInfoList) {
        ProductInfoList = productInfoList;
    }
}
