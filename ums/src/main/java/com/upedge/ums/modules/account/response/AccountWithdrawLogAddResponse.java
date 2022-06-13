package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountWithdrawLogAddRequest;
/**
 * @author gx
 */
public class AccountWithdrawLogAddResponse extends BaseResponse {
    public AccountWithdrawLogAddResponse(int code, String msg, Object data, AccountWithdrawLogAddRequest req) {
        super(code,msg,data,req);
    }
}
