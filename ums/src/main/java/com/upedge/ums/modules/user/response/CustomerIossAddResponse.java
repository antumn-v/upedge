package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerIossAddRequest;
/**
 * @author gx
 */
public class CustomerIossAddResponse extends BaseResponse {
    public CustomerIossAddResponse(int code, String msg, Object data, CustomerIossAddRequest req) {
        super(code,msg,data,req);
    }
}
