package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.PaymentLogListRequest;
/**
 * @author gx
 */
public class PaymentLogListResponse extends BaseResponse {
    public PaymentLogListResponse(int code, String msg, Object data,PaymentLogListRequest req) {
        super(code,msg,data,req);
    }
}
