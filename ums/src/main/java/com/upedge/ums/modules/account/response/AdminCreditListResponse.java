package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AdminCreditListRequest;

/**
 * @author author
 */
public class AdminCreditListResponse extends BaseResponse {
    public AdminCreditListResponse(int code, String msg, Object data, AdminCreditListRequest req) {
        super(code,msg,data,req);
    }
}
