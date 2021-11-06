package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.StoreOrderItemAddRequest;

/**
 * @author author
 */
public class StoreOrderItemAddResponse extends BaseResponse {
    public StoreOrderItemAddResponse(int code, String msg, Object data, StoreOrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
