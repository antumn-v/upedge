package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundItemAddRequest;

/**
 * @author author
 */
public class WholesaleRefundItemAddResponse extends BaseResponse {
    public WholesaleRefundItemAddResponse(int code, String msg, Object data, WholesaleRefundItemAddRequest req) {
        super(code,msg,data,req);
    }
}
