package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.RoleAddRequest;
/**
 * @author gx
 */
public class RoleAddResponse extends BaseResponse {
    public RoleAddResponse(int code, String msg, Object data, RoleAddRequest req) {
        super(code,msg,data,req);
    }
}
