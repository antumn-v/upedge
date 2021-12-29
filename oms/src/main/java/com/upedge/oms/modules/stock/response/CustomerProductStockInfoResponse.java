package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class CustomerProductStockInfoResponse extends BaseResponse {
    public CustomerProductStockInfoResponse(int code, String msg) {
        super(code, msg);
    }

    public CustomerProductStockInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
}
