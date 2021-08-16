package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.RolePermissionListRequest;
/**
 * @author gx
 */
public class RolePermissionListResponse extends BaseResponse {
    public RolePermissionListResponse(int code, String msg, Object data,RolePermissionListRequest req) {
        super(code,msg,data,req);
    }
}
