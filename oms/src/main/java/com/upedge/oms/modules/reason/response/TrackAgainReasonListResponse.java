package com.upedge.oms.modules.reason.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.reason.request.TrackAgainReasonListRequest;

/**
 * @author author
 */
public class TrackAgainReasonListResponse extends BaseResponse {
    public TrackAgainReasonListResponse(int code, String msg, Object data, TrackAgainReasonListRequest req) {
        super(code,msg,data,req);
    }
}
