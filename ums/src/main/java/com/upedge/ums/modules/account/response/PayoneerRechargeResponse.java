package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class PayoneerRechargeResponse extends BaseResponse {

    public PayoneerRechargeResponse(int code, String msg) {
        super(code, msg);
    }

    public PayoneerRechargeResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
