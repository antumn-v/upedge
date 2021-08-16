package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.MenuAddRequest;
/**
 * @author gx
 */
public class MenuAddResponse extends BaseResponse {
    public MenuAddResponse(int code, String msg, Object data, MenuAddRequest req) {
        super(code,msg,data,req);
    }
}
