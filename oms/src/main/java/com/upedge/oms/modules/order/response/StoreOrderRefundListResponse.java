package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.StoreOrderRefundListRequest;

/**
 * @author author
 */
public class StoreOrderRefundListResponse extends BaseResponse {
    public StoreOrderRefundListResponse(int code, String msg, Object data, StoreOrderRefundListRequest req) {
        super(code,msg,data,req);
    }
}
