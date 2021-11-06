package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.StoreOrderRefundItemAddRequest;

/**
 * @author author
 */
public class StoreOrderRefundItemAddResponse extends BaseResponse {
    public StoreOrderRefundItemAddResponse(int code, String msg, Object data, StoreOrderRefundItemAddRequest req) {
        super(code,msg,data,req);
    }
}
