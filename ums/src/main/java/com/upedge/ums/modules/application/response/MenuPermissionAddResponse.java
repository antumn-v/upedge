package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.MenuPermissionAddRequest;
/**
 * @author gx
 */
public class MenuPermissionAddResponse extends BaseResponse {
    public MenuPermissionAddResponse(int code, String msg, Object data, MenuPermissionAddRequest req) {
        super(code,msg,data,req);
    }
}
