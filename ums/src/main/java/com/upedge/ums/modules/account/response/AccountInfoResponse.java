package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class AccountInfoResponse extends BaseResponse {
    public AccountInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
}
