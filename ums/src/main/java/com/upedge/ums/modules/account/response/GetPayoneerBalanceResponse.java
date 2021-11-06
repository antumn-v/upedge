package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

public class GetPayoneerBalanceResponse extends BaseResponse {

    public GetPayoneerBalanceResponse(int code, String msg) {
        super(code, msg);
    }

    public GetPayoneerBalanceResponse(int code, String msg, Object data) {
        super(code, data);
    }
}
