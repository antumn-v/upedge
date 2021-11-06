package com.upedge.ums.modules.log.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.log.request.UserOpLogListRequest;

/**
 * @author author
 */
public class UserOpLogListResponse extends BaseResponse {
    public UserOpLogListResponse(int code, String msg, Object data, UserOpLogListRequest req) {
        super(code,msg,data,req);
    }
}
