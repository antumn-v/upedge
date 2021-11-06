package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.PaypalCarriersAddRequest;

/**
 * @author author
 */
public class PaypalCarriersAddResponse extends BaseResponse {
    public PaypalCarriersAddResponse(int code, String msg, Object data, PaypalCarriersAddRequest req) {
        super(code,msg,data,req);
    }
}
