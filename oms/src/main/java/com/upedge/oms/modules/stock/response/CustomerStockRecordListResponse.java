package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.CustomerStockRecordListRequest;

/**
 * @author author
 */
public class CustomerStockRecordListResponse extends BaseResponse {
    public CustomerStockRecordListResponse(int code, String msg, Object data, CustomerStockRecordListRequest req) {
        super(code,msg,data,req);
    }
}
