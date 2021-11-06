package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderAddressAddRequest;

/**
 * @author author
 */
public class WholesaleOrderAddressAddResponse extends BaseResponse {
    public WholesaleOrderAddressAddResponse(int code, String msg, Object data, WholesaleOrderAddressAddRequest req) {
        super(code,msg,data,req);
    }
}
