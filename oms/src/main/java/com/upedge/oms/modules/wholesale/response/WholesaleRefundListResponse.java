package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundListRequest;

/**
 * @author author
 */
public class WholesaleRefundListResponse extends BaseResponse {
    public WholesaleRefundListResponse(int code, String msg, Object data, WholesaleRefundListRequest req) {
        super(code,msg,data,req);
    }
}
