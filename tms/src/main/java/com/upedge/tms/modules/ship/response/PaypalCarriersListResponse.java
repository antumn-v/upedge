package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.entity.PaypalCarriers;
import com.upedge.tms.modules.ship.request.PaypalCarriersListRequest;

import java.util.List;

/**
 * @author author
 */
public class PaypalCarriersListResponse extends BaseResponse {
    public PaypalCarriersListResponse(int code, String msg, Object data) {
        super(code,msg,data);
    }

    public PaypalCarriersListResponse(int code, String msg, List<PaypalCarriers> results, PaypalCarriersListRequest request) {
        super(code,msg,results,request);
    }
}
