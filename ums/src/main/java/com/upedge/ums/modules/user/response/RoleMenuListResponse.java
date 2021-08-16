package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.RoleMenuListRequest;
/**
 * @author gx
 */
public class RoleMenuListResponse extends BaseResponse {
    public RoleMenuListResponse(int code, String msg, Object data,RoleMenuListRequest req) {
        super(code,msg,data,req);
    }
}
