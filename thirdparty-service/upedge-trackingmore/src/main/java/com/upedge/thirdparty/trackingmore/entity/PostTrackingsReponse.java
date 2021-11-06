package com.upedge.thirdparty.trackingmore.entity;


import com.upedge.thirdparty.trackingmore.entity.webhook.TrackingData;

/**
 * Created by cjq on 2019/5/8.
 */
public class PostTrackingsReponse {

  private Meta meta;
  private TrackingData data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public TrackingData getData() {
        return data;
    }

    public void setData(TrackingData data) {
        this.data = data;
    }
}
