package com.upedge.oms.modules.pick.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pick.request.OrderPickAddRequest;
/**
 * @author gx
 */
public class OrderPickAddResponse extends BaseResponse {
    public OrderPickAddResponse(int code, String msg, Object data, OrderPickAddRequest req) {
        super(code,msg,data,req);
    }
}
