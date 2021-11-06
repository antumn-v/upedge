package com.upedge.thirdparty.trackingmore.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.upedge.thirdparty.trackingmore.entity.GetTracking;
import com.upedge.thirdparty.trackingmore.entity.PostTracking;
import com.upedge.thirdparty.trackingmore.entity.webhook.TrackingData;
import com.upedge.thirdparty.trackingmore.service.TrackerService;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

public class TrackingMoreApi {

    public static GetTracking refreshTrackState(PostTracking tracking) {
        String carrierCode = tracking.getCarrier_code();
        String trackingNumber = tracking.getTracking_number();
        if (!StringUtils.isBlank(carrierCode)
                && !StringUtils.isBlank(trackingNumber)) {
            //真实接口
            TrackingData trackingData = TrackerService.createTrackingNumber(tracking);
            if (trackingData != null) {
                //查询追踪信息
                //真实接口
                GetTracking getTracking = TrackerService.getTrackingResultsOfASingleTracking(
                        carrierCode, trackingNumber);
                //同步上传状态
                String trackStatus = null;
                Date onlineTime = null, signedTime = null;
                if (getTracking != null) {
                    trackStatus = getTracking.getStatus();
                    if (getTracking.getOriginInfo() != null) {
                        onlineTime = getTracking.getOriginInfo().getItemReceived();
                    }
                    if (trackStatus != null && trackStatus.equals("delivered")) {
                        //获取签收的最终时间
                        signedTime = getTracking.getLastUpdateTime();
                    } else {
                        //有些物流信息状态可能不准确 签收信息需要从trackInfo查找
                        if (getTracking.getOriginInfo() != null) {
                            String trackInfo = getTracking.getOriginInfo().getTrackinfo();
                            if (!StringUtils.isBlank(trackInfo)) {
                                JSONArray objects = JSON.parseArray(trackInfo);
                                if (objects != null && objects.size() > 0) {
                                    //获取最新一条状态
                                    JSONObject jsonObject = objects.getJSONObject(0);
                                    String res = jsonObject.toJSONString();
                                    if (res.contains("delivered")) {
                                        trackStatus = "delivered";
                                        getTracking.setStatus(trackStatus);
                                        //获取签收的最终时间
                                        signedTime = getTracking.getLastUpdateTime();
                                    }
                                }
                            }
                        }
                    }

                }
                return getTracking;
            }
        }
        return null;
    }
}
