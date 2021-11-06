package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderAddressListRequest;

/**
 * @author author
 */
public class WholesaleOrderAddressListResponse extends BaseResponse {
    public WholesaleOrderAddressListResponse(int code, String msg, Object data, WholesaleOrderAddressListRequest req) {
        super(code,msg,data,req);
    }
}
