package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleTrackingListRequest;

/**
 * @author author
 */
public class WholesaleTrackingListResponse extends BaseResponse {
    public WholesaleTrackingListResponse(int code, String msg, Object data, WholesaleTrackingListRequest req) {
        super(code,msg,data,req);
    }
}
