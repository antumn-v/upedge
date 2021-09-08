package com.upedge.thirdparty.saihe.entity.processUpdateProduct;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/3/29.
 */
public class ImportProductList {

    List<ApiImportProductInfo > ImportProductList=new ArrayList<>();

    @XmlElement(name="ApiImportProductInfo")
    public List<ApiImportProductInfo> getImportProductList() {
        return ImportProductList;
    }

    public void setImportProductList(List<ApiImportProductInfo> importProductList) {
        ImportProductList = importProductList;
    }
}
