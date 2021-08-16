package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.PaymentLogAddRequest;
/**
 * @author gx
 */
public class PaymentLogAddResponse extends BaseResponse {
    public PaymentLogAddResponse(int code, String msg, Object data, PaymentLogAddRequest req) {
        super(code,msg,data,req);
    }
}
