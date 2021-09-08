package com.upedge.thirdparty.saihe.entity.getTransportList;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cjq on 2019/1/11.
 */
public class TransportList {


    List<ApiTransport> ApiTransport=new ArrayList<>();

    @XmlElement(name="ApiTransport")
    public List<ApiTransport> getApiTransport() {
        return ApiTransport;
    }

    public void setApiTransport(List<ApiTransport> apiTransport) {
        ApiTransport = apiTransport;
    }
}
