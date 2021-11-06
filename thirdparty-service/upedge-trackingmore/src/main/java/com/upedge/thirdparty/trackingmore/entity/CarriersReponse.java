package com.upedge.thirdparty.trackingmore.entity;

import java.util.List;

/**
 * Created by cjq on 2019/5/8.
 */
public class CarriersReponse {

    private Meta meta;
    private List<Carrier> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Carrier> getData() {
        return data;
    }

    public void setData(List<Carrier> data) {
        this.data = data;
    }
}
