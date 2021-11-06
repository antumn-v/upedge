package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderAddRequest;

/**
 * @author author
 */
public class OrderAddResponse extends BaseResponse {
    public OrderAddResponse(int code, String msg, Object data, OrderAddRequest req) {
        super(code,msg,data,req);
    }
}
