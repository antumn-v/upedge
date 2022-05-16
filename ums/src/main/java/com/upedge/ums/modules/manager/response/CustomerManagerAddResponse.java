package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.CustomerManagerAddRequest;
/**
 * @author gx
 */
public class CustomerManagerAddResponse extends BaseResponse {
    public CustomerManagerAddResponse(int code, String msg, Object data, CustomerManagerAddRequest req) {
        super(code,msg,data,req);
    }
}
