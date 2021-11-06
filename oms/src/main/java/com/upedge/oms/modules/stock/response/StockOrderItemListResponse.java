package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderItemListRequest;

/**
 * @author author
 */
public class StockOrderItemListResponse extends BaseResponse {
    public StockOrderItemListResponse(int code, String msg, Object data, StockOrderItemListRequest req) {
        super(code,msg,data,req);
    }
    public StockOrderItemListResponse(int code, String msg) {
        super(code,msg);
    }
}
