package com.upedge.oms.modules.reason.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.reason.request.RefundReasonListRequest;

/**
 * @author author
 */
public class RefundReasonListResponse extends BaseResponse {
    public RefundReasonListResponse(int code, String msg, Object data, RefundReasonListRequest req) {
        super(code,msg,data,req);
    }
}
