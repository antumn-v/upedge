package com.upedge.oms.modules.common.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.common.request.OrderErrorMessageAddRequest;
/**
 * @author gx
 */
public class OrderErrorMessageAddResponse extends BaseResponse {
    public OrderErrorMessageAddResponse(int code, String msg, Object data, OrderErrorMessageAddRequest req) {
        super(code,msg,data,req);
    }
}
