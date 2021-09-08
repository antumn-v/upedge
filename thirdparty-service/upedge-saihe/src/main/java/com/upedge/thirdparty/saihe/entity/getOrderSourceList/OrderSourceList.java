package com.upedge.thirdparty.saihe.entity.getOrderSourceList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/4/9.
 */
public class OrderSourceList {

    List<ApiOrderSource> apiOrderSource=new ArrayList<>();

    @XmlElement(name="ApiOrderSource")
    public List<ApiOrderSource> getApiOrderSource() {
        return apiOrderSource;
    }

    public void setApiOrderSource(List<ApiOrderSource> apiOrderSource) {
        this.apiOrderSource = apiOrderSource;
    }
}
