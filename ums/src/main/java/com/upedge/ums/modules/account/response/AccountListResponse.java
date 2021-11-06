package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class AccountListResponse extends BaseResponse {

    public AccountListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public AccountListResponse(int code, String msg, Object data, Object req) {
        super(code, msg, data, req);
    }
}
