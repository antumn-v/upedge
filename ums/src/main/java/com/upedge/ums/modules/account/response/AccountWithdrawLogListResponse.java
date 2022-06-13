package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountWithdrawLogListRequest;
/**
 * @author gx
 */
public class AccountWithdrawLogListResponse extends BaseResponse {
    public AccountWithdrawLogListResponse(int code, String msg, Object data,AccountWithdrawLogListRequest req) {
        super(code,msg,data,req);
    }
}
