package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class AccountPayMethodListResponse extends BaseResponse {

    public AccountPayMethodListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public AccountPayMethodListResponse(int code, String msg, Object data, Object req) {
        super(code, msg, data, req);
    }
}
