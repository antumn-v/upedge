package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class PaypalPaymentInfoResponse extends BaseResponse {
    public PaypalPaymentInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
}
