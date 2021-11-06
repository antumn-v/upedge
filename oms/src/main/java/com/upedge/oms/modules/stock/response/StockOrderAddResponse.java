package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockOrderAddRequest;

/**
 * @author author
 */
public class StockOrderAddResponse extends BaseResponse {
    public StockOrderAddResponse(int code, String msg, Object data, StockOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
