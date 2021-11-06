package com.upedge.oms.modules.reason.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.reason.request.RefundReasonAddRequest;

/**
 * @author author
 */
public class RefundReasonAddResponse extends BaseResponse {
    public RefundReasonAddResponse(int code, String msg, Object data, RefundReasonAddRequest req) {
        super(code,msg,data,req);
    }
}
