package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderAddRequest;

/**
 * @author author
 */
public class WholesaleOrderAddResponse extends BaseResponse {
    public WholesaleOrderAddResponse(int code, String msg, Object data, WholesaleOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
