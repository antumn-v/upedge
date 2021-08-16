package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.RoleMenuAddRequest;
/**
 * @author gx
 */
public class RoleMenuAddResponse extends BaseResponse {
    public RoleMenuAddResponse(int code, String msg, Object data, RoleMenuAddRequest req) {
        super(code,msg,data,req);
    }
}
