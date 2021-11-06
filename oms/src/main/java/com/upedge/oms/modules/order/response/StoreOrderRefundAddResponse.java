package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.StoreOrderRefundAddRequest;

/**
 * @author author
 */
public class StoreOrderRefundAddResponse extends BaseResponse {
    public StoreOrderRefundAddResponse(int code, String msg, Object data, StoreOrderRefundAddRequest req) {
        super(code,msg,data,req);
    }
}
