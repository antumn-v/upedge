package com.upedge.thirdparty.saihe.entity.getPackages;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/5/15.
 */
public class ApiPackageList {

    List<ApiPackageInfo> ApiPackageList=new ArrayList<>();

    @XmlElement(name="ApiPackageInfo")
    public List<ApiPackageInfo> getApiPackageList() {
        return ApiPackageList;
    }

    public void setApiPackageList(List<ApiPackageInfo> apiPackageList) {
        ApiPackageList = apiPackageList;
    }
}
