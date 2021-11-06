package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderItemListRequest;

/**
 * @author author
 */
public class OrderItemListResponse extends BaseResponse {
    public OrderItemListResponse(int code, String msg, Object data, OrderItemListRequest req) {
        super(code,msg,data,req);
    }
}
