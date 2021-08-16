package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountListRequest;
/**
 * @author gx
 */
public class AccountListResponse extends BaseResponse {
    public AccountListResponse(int code, String msg, Object data,AccountListRequest req) {
        super(code,msg,data,req);
    }
}
