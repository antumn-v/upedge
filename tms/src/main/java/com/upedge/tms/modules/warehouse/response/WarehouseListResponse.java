package com.upedge.tms.modules.warehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.warehouse.request.WarehouseListRequest;
/**
 * @author gx
 */
public class WarehouseListResponse extends BaseResponse {
    public WarehouseListResponse(int code, String msg, Object data,WarehouseListRequest req) {
        super(code,msg,data,req);
    }
}
