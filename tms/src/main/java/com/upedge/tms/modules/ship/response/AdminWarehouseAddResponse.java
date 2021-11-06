package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.AdminWarehouseAddRequest;

/**
 * @author author
 */
public class AdminWarehouseAddResponse extends BaseResponse {
    public AdminWarehouseAddResponse(int code, String msg, Object data, AdminWarehouseAddRequest req) {
        super(code,msg,data,req);
    }
}
