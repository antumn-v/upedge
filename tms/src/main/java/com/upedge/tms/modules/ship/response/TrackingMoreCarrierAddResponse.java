package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.TrackingMoreCarrierAddRequest;

/**
 * @author author
 */
public class TrackingMoreCarrierAddResponse extends BaseResponse {
    public TrackingMoreCarrierAddResponse(int code, String msg, Object data, TrackingMoreCarrierAddRequest req) {
        super(code,msg,data,req);
    }
}
