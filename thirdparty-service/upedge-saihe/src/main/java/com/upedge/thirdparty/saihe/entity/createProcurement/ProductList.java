package com.upedge.thirdparty.saihe.entity.createProcurement;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by cjq on 2019/8/29.
 */
public class ProductList {

    List<ApiCreateProcurementProductList> ProductList;//备库单产品

    @XmlElement(name="ApiCreateProcurementProductList")
    public List<ApiCreateProcurementProductList> getProductList() {
        return ProductList;
    }

    public void setProductList(List<ApiCreateProcurementProductList> productList) {
        ProductList = productList;
    }

}
