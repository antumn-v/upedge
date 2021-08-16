package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.RolePermissionAddRequest;
/**
 * @author gx
 */
public class RolePermissionAddResponse extends BaseResponse {
    public RolePermissionAddResponse(int code, String msg, Object data, RolePermissionAddRequest req) {
        super(code,msg,data,req);
    }
}
