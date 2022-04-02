package com.upedge.tms.modules.warehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.warehouse.request.CountryAvailableWarehouseListRequest;
/**
 * @author gx
 */
public class CountryAvailableWarehouseListResponse extends BaseResponse {
    public CountryAvailableWarehouseListResponse(int code, String msg, Object data,CountryAvailableWarehouseListRequest req) {
        super(code,msg,data,req);
    }
}
