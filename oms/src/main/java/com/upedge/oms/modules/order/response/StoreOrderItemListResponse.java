package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.order.request.StoreOrderItemListRequest;

/**
 * @author author
 */
public class StoreOrderItemListResponse extends BaseResponse {
    public StoreOrderItemListResponse(int code, String msg, Object data, StoreOrderItemListRequest req) {
        super(code,msg,data,req);
    }
}
