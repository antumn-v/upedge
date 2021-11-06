package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderItemAddRequest;

/**
 * @author author
 */
public class StockOrderItemAddResponse extends BaseResponse {
    public StockOrderItemAddResponse(int code, String msg, Object data, StockOrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
