package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderListRequest;

/**
 * @author author
 */
public class WholesaleOrderListResponse extends BaseResponse {
    public WholesaleOrderListResponse(int code, String msg, Object data, WholesaleOrderListRequest req) {
        super(code,msg,data,req);
    }
    public WholesaleOrderListResponse(int code, String msg) {
        super(code,msg);
    }
}
