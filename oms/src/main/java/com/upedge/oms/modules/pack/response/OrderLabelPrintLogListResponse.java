package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderLabelPrintLogListRequest;
/**
 * @author gx
 */
public class OrderLabelPrintLogListResponse extends BaseResponse {
    public OrderLabelPrintLogListResponse(int code, String msg, Object data,OrderLabelPrintLogListRequest req) {
        super(code,msg,data,req);
    }
}
