package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.MenuPermissionListRequest;
/**
 * @author gx
 */
public class MenuPermissionListResponse extends BaseResponse {
    public MenuPermissionListResponse(int code, String msg, Object data,MenuPermissionListRequest req) {
        super(code,msg,data,req);
    }
}
