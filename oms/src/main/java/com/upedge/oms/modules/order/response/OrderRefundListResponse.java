package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderRefundListRequest;

/**
 * @author author
 */
public class OrderRefundListResponse extends BaseResponse {
    public OrderRefundListResponse(int code, String msg, Object data, OrderRefundListRequest req) {
        super(code,msg,data,req);
    }
}
