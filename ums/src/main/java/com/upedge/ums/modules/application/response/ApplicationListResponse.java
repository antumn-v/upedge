package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.ApplicationListRequest;
/**
 * @author gx
 */
public class ApplicationListResponse extends BaseResponse {
    public ApplicationListResponse(int code, String msg, Object data,ApplicationListRequest req) {
        super(code,msg,data,req);
    }
}
