package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderProfitListRequest;

/**
 * @author author
 */
public class OrderProfitListResponse extends BaseResponse {
    public OrderProfitListResponse(int code, String msg, Object data, OrderProfitListRequest req) {
        super(code,msg,data,req);
    }
}
