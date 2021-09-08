package com.upedge.ums.modules.application.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.application.request.TPermissionListRequest;
/**
 * @author gx
 */
public class TPermissionListResponse extends BaseResponse {
    public TPermissionListResponse(int code, String msg, Object data,TPermissionListRequest req) {
        super(code,msg,data,req);
    }
}
