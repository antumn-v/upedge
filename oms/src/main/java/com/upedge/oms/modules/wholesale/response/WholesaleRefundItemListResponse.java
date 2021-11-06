package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleRefundItemListRequest;

/**
 * @author author
 */
public class WholesaleRefundItemListResponse extends BaseResponse {
    public WholesaleRefundItemListResponse(int code, String msg, Object data, WholesaleRefundItemListRequest req) {
        super(code,msg,data,req);
    }
}
