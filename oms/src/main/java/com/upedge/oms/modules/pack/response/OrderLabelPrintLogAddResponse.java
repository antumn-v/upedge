package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderLabelPrintLogAddRequest;
/**
 * @author gx
 */
public class OrderLabelPrintLogAddResponse extends BaseResponse {
    public OrderLabelPrintLogAddResponse(int code, String msg, Object data, OrderLabelPrintLogAddRequest req) {
        super(code,msg,data,req);
    }
}
