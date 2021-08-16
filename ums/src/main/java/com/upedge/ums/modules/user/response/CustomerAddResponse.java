package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerAddRequest;
/**
 * @author gx
 */
public class CustomerAddResponse extends BaseResponse {
    public CustomerAddResponse(int code, String msg, Object data, CustomerAddRequest req) {
        super(code,msg,data,req);
    }
}
