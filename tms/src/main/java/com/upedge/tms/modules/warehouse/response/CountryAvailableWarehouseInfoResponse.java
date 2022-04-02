package com.upedge.tms.modules.warehouse.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class CountryAvailableWarehouseInfoResponse extends BaseResponse {
    public CountryAvailableWarehouseInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
}
