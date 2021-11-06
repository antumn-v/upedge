package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderRefundListRequest;

/**
 * @author author
 */
public class StockOrderRefundListResponse extends BaseResponse {
    public StockOrderRefundListResponse(int code, String msg, Object data, StockOrderRefundListRequest req) {
        super(code,msg,data,req);
    }
}
