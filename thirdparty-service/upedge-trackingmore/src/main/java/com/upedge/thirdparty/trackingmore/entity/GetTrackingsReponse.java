package com.upedge.thirdparty.trackingmore.entity;

/**
 * Created by cjq on 2019/5/8.
 */
public class GetTrackingsReponse {

    private Meta meta;
    private GetTracking data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public GetTracking getData() {
        return data;
    }

    public void setData(GetTracking data) {
        this.data = data;
    }
}
