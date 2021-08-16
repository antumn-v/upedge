package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.MenuListRequest;
/**
 * @author gx
 */
public class MenuListResponse extends BaseResponse {
    public MenuListResponse(int code, String msg, Object data,MenuListRequest req) {
        super(code,msg,data,req);
    }
}
