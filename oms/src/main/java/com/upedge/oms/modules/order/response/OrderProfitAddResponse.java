package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderProfitAddRequest;

/**
 * @author author
 */
public class OrderProfitAddResponse extends BaseResponse {
    public OrderProfitAddResponse(int code, String msg, Object data, OrderProfitAddRequest req) {
        super(code,msg,data,req);
    }
}
