package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountUserListRequest;
/**
 * @author gx
 */
public class AccountUserListResponse extends BaseResponse {
    public AccountUserListResponse(int code, String msg, Object data,AccountUserListRequest req) {
        super(code,msg,data,req);
    }
}
