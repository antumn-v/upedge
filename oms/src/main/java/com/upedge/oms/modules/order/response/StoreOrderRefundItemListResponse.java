package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.StoreOrderRefundItemListRequest;

/**
 * @author author
 */
public class StoreOrderRefundItemListResponse extends BaseResponse {
    public StoreOrderRefundItemListResponse(int code, String msg, Object data, StoreOrderRefundItemListRequest req) {
        super(code,msg,data,req);
    }
}
