package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderRefundItemAddRequest;

/**
 * @author author
 */
public class StockOrderRefundItemAddResponse extends BaseResponse {
    public StockOrderRefundItemAddResponse(int code, String msg, Object data, StockOrderRefundItemAddRequest req) {
        super(code,msg,data,req);
    }
}
