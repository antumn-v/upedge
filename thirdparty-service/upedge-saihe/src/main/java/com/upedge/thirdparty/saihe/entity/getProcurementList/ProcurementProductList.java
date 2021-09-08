package com.upedge.thirdparty.saihe.entity.getProcurementList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaqi on 2020/7/21.
 */
public class ProcurementProductList {

    List<ApiProcurementProductList> apiProcurementProductListList=new ArrayList();

    @XmlElement(name="ApiProcurementProductList")
    public List<ApiProcurementProductList> getApiProcurementProductListList() {
        return apiProcurementProductListList;
    }

    public void setApiProcurementProductListList(List<ApiProcurementProductList> apiProcurementProductListList) {
        this.apiProcurementProductListList = apiProcurementProductListList;
    }
}
