package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderRefundItemAddRequest;

/**
 * @author author
 */
public class OrderRefundItemAddResponse extends BaseResponse {
    public OrderRefundItemAddResponse(int code, String msg, Object data, OrderRefundItemAddRequest req) {
        super(code,msg,data,req);
    }
}
