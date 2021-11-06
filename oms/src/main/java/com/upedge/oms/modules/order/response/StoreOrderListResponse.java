package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class StoreOrderListResponse extends BaseResponse {
    public StoreOrderListResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
    public StoreOrderListResponse(int code, String msg) {
        super(code,msg);
    }
}
