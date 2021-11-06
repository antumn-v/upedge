package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.AdminWarehouseListRequest;

/**
 * @author author
 */
public class AdminWarehouseListResponse extends BaseResponse {
    public AdminWarehouseListResponse(int code, String msg, Object data, AdminWarehouseListRequest req) {
        super(code,msg,data,req);
    }
}
