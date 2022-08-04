package com.upedge.oms.modules.pick.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pick.request.OrderPickListRequest;
/**
 * @author gx
 */
public class OrderPickListResponse extends BaseResponse {
    public OrderPickListResponse(int code, String msg, Object data,OrderPickListRequest req) {
        super(code,msg,data,req);
    }
}
