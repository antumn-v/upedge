package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderRefundItemListRequest;

/**
 * @author author
 */
public class StockOrderRefundItemListResponse extends BaseResponse {
    public StockOrderRefundItemListResponse(int code, String msg, Object data, StockOrderRefundItemListRequest req) {
        super(code,msg,data,req);
    }
}
