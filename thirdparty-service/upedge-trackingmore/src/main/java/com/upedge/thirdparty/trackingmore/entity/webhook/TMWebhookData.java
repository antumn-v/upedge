package com.upedge.thirdparty.trackingmore.entity.webhook;

import com.upedge.thirdparty.trackingmore.entity.Meta;
import lombok.Data;

/**
 * Created by cjq on 2019/5/29.
 */
@Data
public class TMWebhookData {

    private Meta meta;

    private TrackingData data;

    private VerifyInfo verifyInfo;


}
