package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerTargetListRequest;

/**
 * @author author
 */
public class ManagerTargetListResponse extends BaseResponse {
    public ManagerTargetListResponse(int code, String msg, Object data, ManagerTargetListRequest req) {
        super(code,msg,data,req);
    }
}
