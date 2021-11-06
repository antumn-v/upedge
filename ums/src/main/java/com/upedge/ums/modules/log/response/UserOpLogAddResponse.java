package com.upedge.ums.modules.log.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.log.request.UserOpLogAddRequest;

/**
 * @author author
 */
public class UserOpLogAddResponse extends BaseResponse {
    public UserOpLogAddResponse(int code, String msg, Object data, UserOpLogAddRequest req) {
        super(code,msg,data,req);
    }
}
