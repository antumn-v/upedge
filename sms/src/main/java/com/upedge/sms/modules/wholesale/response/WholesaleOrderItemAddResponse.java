package com.upedge.sms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderItemAddRequest;
/**
 * @author gx
 */
public class WholesaleOrderItemAddResponse extends BaseResponse {
    public WholesaleOrderItemAddResponse(int code, String msg, Object data, WholesaleOrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
