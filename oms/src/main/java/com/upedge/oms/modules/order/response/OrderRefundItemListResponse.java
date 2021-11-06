package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.OrderRefundItemListRequest;

/**
 * @author author
 */
public class OrderRefundItemListResponse extends BaseResponse {
    public OrderRefundItemListResponse(int code, String msg, Object data, OrderRefundItemListRequest req) {
        super(code,msg,data,req);
    }
}
