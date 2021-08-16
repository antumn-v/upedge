package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AccountUserAddRequest;
/**
 * @author gx
 */
public class AccountUserAddResponse extends BaseResponse {
    public AccountUserAddResponse(int code, String msg, Object data, AccountUserAddRequest req) {
        super(code,msg,data,req);
    }
}
