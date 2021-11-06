package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleTrackingAddRequest;

/**
 * @author author
 */
public class WholesaleTrackingAddResponse extends BaseResponse {
    public WholesaleTrackingAddResponse(int code, String msg, Object data, WholesaleTrackingAddRequest req) {
        super(code,msg,data,req);
    }
}
