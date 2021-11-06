package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderRefundAddRequest;

/**
 * @author author
 */
public class OrderRefundAddResponse extends BaseResponse {
    public OrderRefundAddResponse(int code, String msg, Object data, OrderRefundAddRequest req) {
        super(code,msg,data,req);
    }
}
