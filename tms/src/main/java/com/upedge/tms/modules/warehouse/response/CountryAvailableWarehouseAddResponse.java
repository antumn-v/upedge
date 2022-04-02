package com.upedge.tms.modules.warehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.warehouse.request.CountryAvailableWarehouseAddRequest;
/**
 * @author gx
 */
public class CountryAvailableWarehouseAddResponse extends BaseResponse {
    public CountryAvailableWarehouseAddResponse(int code, String msg, Object data, CountryAvailableWarehouseAddRequest req) {
        super(code,msg,data,req);
    }
}
