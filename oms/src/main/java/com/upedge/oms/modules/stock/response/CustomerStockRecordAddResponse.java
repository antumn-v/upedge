package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.CustomerStockRecordAddRequest;

/**
 * @author author
 */
public class CustomerStockRecordAddResponse extends BaseResponse {
    public CustomerStockRecordAddResponse(int code, String msg, Object data, CustomerStockRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
