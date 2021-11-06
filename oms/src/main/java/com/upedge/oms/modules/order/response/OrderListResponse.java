package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class OrderListResponse extends BaseResponse {
    public OrderListResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
    public OrderListResponse(int code, String msg) {
        super(code,msg);
    }
}
