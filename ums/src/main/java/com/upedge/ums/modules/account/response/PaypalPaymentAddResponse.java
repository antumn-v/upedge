package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.PaypalPaymentAddRequest;
/**
 * @author gx
 */
public class PaypalPaymentAddResponse extends BaseResponse {
    public PaypalPaymentAddResponse(int code, String msg, Object data, PaypalPaymentAddRequest req) {
        super(code,msg,data,req);
    }
}
