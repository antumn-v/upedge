package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.RoleListRequest;
/**
 * @author gx
 */
public class RoleListResponse extends BaseResponse {
    public RoleListResponse(int code, String msg, Object data,RoleListRequest req) {
        super(code,msg,data,req);
    }
}
