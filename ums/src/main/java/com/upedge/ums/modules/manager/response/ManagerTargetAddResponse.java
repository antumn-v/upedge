package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerTargetAddRequest;

/**
 * @author author
 */
public class ManagerTargetAddResponse extends BaseResponse {
    public ManagerTargetAddResponse(int code, String msg, Object data, ManagerTargetAddRequest req) {
        super(code,msg,data,req);
    }
    public ManagerTargetAddResponse(int code, String msg) {
        super(code,msg);
    }
}
