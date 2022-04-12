package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderAddRequest;
/**
 * @author gx
 */
public class OverseaWarehouseServiceOrderAddResponse extends BaseResponse {
    public OverseaWarehouseServiceOrderAddResponse(int code, String msg, Object data, OverseaWarehouseServiceOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
