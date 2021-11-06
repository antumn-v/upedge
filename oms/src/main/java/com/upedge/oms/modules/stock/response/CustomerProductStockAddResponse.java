package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.CustomerProductStockAddRequest;

/**
 * @author author
 */
public class CustomerProductStockAddResponse extends BaseResponse {
    public CustomerProductStockAddResponse(int code, String msg, Object data, CustomerProductStockAddRequest req) {
        super(code,msg,data,req);
    }
}
