package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderFreightListRequest;
/**
 * @author gx
 */
public class OverseaWarehouseServiceOrderFreightListResponse extends BaseResponse {
    public OverseaWarehouseServiceOrderFreightListResponse(int code, String msg, Object data,OverseaWarehouseServiceOrderFreightListRequest req) {
        super(code,msg,data,req);
    }
}
