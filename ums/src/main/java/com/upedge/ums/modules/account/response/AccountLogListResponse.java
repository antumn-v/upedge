package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountLogListRequest;

/**
 * @author author
 */
public class AccountLogListResponse extends BaseResponse {
    public AccountLogListResponse(int code, String msg, Object data, AccountLogListRequest req) {
        super(code,msg,data,req);
    }
    public AccountLogListResponse(int code, String msg, Object data) {
        super(code,msg,data);
    }
}
