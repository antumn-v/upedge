package com.upedge.ums.modules.address.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.address.request.CustomerAddressAddRequest;
/**
 * @author gx
 */
public class CustomerAddressAddResponse extends BaseResponse {
    public CustomerAddressAddResponse(int code, String msg, Object data, CustomerAddressAddRequest req) {
        super(code,msg,data,req);
    }
}
