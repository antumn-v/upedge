package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderItemAddRequest;

/**
 * @author author
 */
public class WholesaleOrderItemAddResponse extends BaseResponse {
    public WholesaleOrderItemAddResponse(int code, String msg, Object data, WholesaleOrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
