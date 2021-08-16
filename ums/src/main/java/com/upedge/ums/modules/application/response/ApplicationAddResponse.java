package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.ApplicationAddRequest;
/**
 * @author gx
 */
public class ApplicationAddResponse extends BaseResponse {
    public ApplicationAddResponse(int code, String msg, Object data, ApplicationAddRequest req) {
        super(code,msg,data,req);
    }
}
