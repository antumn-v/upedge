package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.TrackingMoreCarrierListRequest;

/**
 * @author author
 */
public class TrackingMoreCarrierListResponse extends BaseResponse {
    public TrackingMoreCarrierListResponse(int code, String msg, Object data, TrackingMoreCarrierListRequest req) {
        super(code,msg,data,req);
    }

    public TrackingMoreCarrierListResponse(int code, String msg, Object data) {
        super(code,msg,data);
    }
}
