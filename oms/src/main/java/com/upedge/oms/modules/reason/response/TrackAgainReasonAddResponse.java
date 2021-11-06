package com.upedge.oms.modules.reason.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.reason.request.TrackAgainReasonAddRequest;

/**
 * @author author
 */
public class TrackAgainReasonAddResponse extends BaseResponse {
    public TrackAgainReasonAddResponse(int code, String msg, Object data, TrackAgainReasonAddRequest req) {
        super(code,msg,data,req);
    }
}
