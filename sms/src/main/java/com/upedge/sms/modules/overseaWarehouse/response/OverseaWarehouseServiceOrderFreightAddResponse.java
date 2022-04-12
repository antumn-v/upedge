package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderFreightAddRequest;
/**
 * @author gx
 */
public class OverseaWarehouseServiceOrderFreightAddResponse extends BaseResponse {
    public OverseaWarehouseServiceOrderFreightAddResponse(int code, String msg, Object data, OverseaWarehouseServiceOrderFreightAddRequest req) {
        super(code,msg,data,req);
    }
}
