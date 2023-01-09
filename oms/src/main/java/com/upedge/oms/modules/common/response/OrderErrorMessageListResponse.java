package com.upedge.oms.modules.common.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.common.request.OrderErrorMessageListRequest;
/**
 * @author gx
 */
public class OrderErrorMessageListResponse extends BaseResponse {
    public OrderErrorMessageListResponse(int code, String msg, Object data,OrderErrorMessageListRequest req) {
        super(code,msg,data,req);
    }
}
