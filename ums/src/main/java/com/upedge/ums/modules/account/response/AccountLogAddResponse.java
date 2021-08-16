package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountLogAddRequest;
/**
 * @author gx
 */
public class AccountLogAddResponse extends BaseResponse {
    public AccountLogAddResponse(int code, String msg, Object data, AccountLogAddRequest req) {
        super(code,msg,data,req);
    }
}
