package com.upedge.sms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderAddRequest;
/**
 * @author gx
 */
public class WholesaleOrderAddResponse extends BaseResponse {
    public WholesaleOrderAddResponse(int code, String msg, Object data, WholesaleOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
