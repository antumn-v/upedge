package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.PaypalPaymentListRequest;
/**
 * @author gx
 */
public class PaypalPaymentListResponse extends BaseResponse {
    public PaypalPaymentListResponse(int code, String msg, Object data,PaypalPaymentListRequest req) {
        super(code,msg,data,req);
    }
}
