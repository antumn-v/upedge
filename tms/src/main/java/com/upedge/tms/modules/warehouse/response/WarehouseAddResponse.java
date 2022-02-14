package com.upedge.tms.modules.warehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.warehouse.request.WarehouseAddRequest;
/**
 * @author gx
 */
public class WarehouseAddResponse extends BaseResponse {
    public WarehouseAddResponse(int code, String msg, Object data, WarehouseAddRequest req) {
        super(code,msg,data,req);
    }
}
