package com.upedge.ums.modules.address.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.address.request.CustomerAddressListRequest;
/**
 * @author gx
 */
public class CustomerAddressListResponse extends BaseResponse {
    public CustomerAddressListResponse(int code, String msg, Object data,CustomerAddressListRequest req) {
        super(code,msg,data,req);
    }
}
