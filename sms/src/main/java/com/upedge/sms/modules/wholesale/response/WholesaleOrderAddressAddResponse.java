package com.upedge.sms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderAddressAddRequest;
/**
 * @author gx
 */
public class WholesaleOrderAddressAddResponse extends BaseResponse {
    public WholesaleOrderAddressAddResponse(int code, String msg, Object data, WholesaleOrderAddressAddRequest req) {
        super(code,msg,data,req);
    }
}
