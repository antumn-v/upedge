package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.StoreOrderAddRequest;

/**
 * @author author
 */
public class StoreOrderAddResponse extends BaseResponse {
    public StoreOrderAddResponse(int code, String msg, Object data, StoreOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
