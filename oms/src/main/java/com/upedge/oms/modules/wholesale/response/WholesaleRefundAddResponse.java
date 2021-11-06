package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundAddRequest;

/**
 * @author author
 */
public class WholesaleRefundAddResponse extends BaseResponse {
    public WholesaleRefundAddResponse(int code, String msg, Object data, WholesaleRefundAddRequest req) {
        super(code,msg,data,req);
    }
}
