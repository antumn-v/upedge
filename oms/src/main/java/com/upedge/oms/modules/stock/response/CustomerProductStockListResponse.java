package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.CustomerProductStockListRequest;

/**
 * @author author
 */
public class CustomerProductStockListResponse extends BaseResponse {
    public CustomerProductStockListResponse(int code, String msg, Object data, CustomerProductStockListRequest req) {
        super(code,msg,data,req);
    }
}
