package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountAddRequest;
/**
 * @author gx
 */
public class AccountAddResponse extends BaseResponse {
    public AccountAddResponse(int code, String msg, Object data, AccountAddRequest req) {
        super(code,msg,data,req);
    }
}
