package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderItemAddRequest;

/**
 * @author author
 */
public class OrderItemAddResponse extends BaseResponse {
    public OrderItemAddResponse(int code, String msg, Object data, OrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
