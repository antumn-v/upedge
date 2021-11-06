package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderRefundAddRequest;

/**
 * @author author
 */
public class StockOrderRefundAddResponse extends BaseResponse {
    public StockOrderRefundAddResponse(int code, String msg, Object data, StockOrderRefundAddRequest req) {
        super(code,msg,data,req);
    }
}
