package com.upedge.sms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderAddressListRequest;
/**
 * @author gx
 */
public class WholesaleOrderAddressListResponse extends BaseResponse {
    public WholesaleOrderAddressListResponse(int code, String msg, Object data,WholesaleOrderAddressListRequest req) {
        super(code,msg,data,req);
    }
}
