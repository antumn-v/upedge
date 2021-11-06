package com.upedge.thirdparty.trackingmore.entity.webhook;

/**
 * Created by cjq on 2019/5/29.
 */
public class VerifyInfo {

    String timeStr;
    String signature;

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
