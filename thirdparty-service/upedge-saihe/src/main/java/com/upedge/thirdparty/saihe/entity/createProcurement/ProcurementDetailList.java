package com.upedge.thirdparty.saihe.entity.createProcurement;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoxing on 2019/9/9.
 */
public class ProcurementDetailList {

    List<ApiProcurementDetailList> ProcurementDetailList=new ArrayList<>();

    @XmlElement(name="ApiProcurementDetailList")
    public List<ApiProcurementDetailList> getProcurementDetailList() {
        return ProcurementDetailList;
    }

    public void setProcurementDetailList(List<ApiProcurementDetailList> procurementDetailList) {
        ProcurementDetailList = procurementDetailList;
    }
}
