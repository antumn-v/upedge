package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AdminAccountListRequest;

/**
 * @author author
 */
public class AdminAccountListResponse extends BaseResponse {
    public AdminAccountListResponse(int code, String msg, Object data, AdminAccountListRequest req) {
        super(code,msg,data,req);
    }
}
