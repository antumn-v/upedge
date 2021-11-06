package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AdminAccountAddRequest;

/**
 * @author author
 */
public class AdminAccountAddResponse extends BaseResponse {
    public AdminAccountAddResponse(int code, String msg, Object data, AdminAccountAddRequest req) {
        super(code,msg,data,req);
    }
}
