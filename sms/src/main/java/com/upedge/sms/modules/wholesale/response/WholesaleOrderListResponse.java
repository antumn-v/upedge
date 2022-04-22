package com.upedge.sms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderListRequest;
/**
 * @author gx
 */
public class WholesaleOrderListResponse extends BaseResponse {
    public WholesaleOrderListResponse(int code, String msg, Object data,WholesaleOrderListRequest req) {
        super(code,msg,data,req);
    }
}
