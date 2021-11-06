package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleReshipInfoAddRequest;

/**
 * @author author
 */
public class WholesaleReshipInfoAddResponse extends BaseResponse {
    public WholesaleReshipInfoAddResponse(int code, String msg, Object data, WholesaleReshipInfoAddRequest req) {
        super(code,msg,data,req);
    }
}
