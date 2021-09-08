package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.TPermissionAddRequest;
/**
 * @author gx
 */
public class TPermissionAddResponse extends BaseResponse {
    public TPermissionAddResponse(int code, String msg, Object data, TPermissionAddRequest req) {
        super(code,msg,data,req);
    }
}
