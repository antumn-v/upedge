package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseSkuAddRequest;
/**
 * @author gx
 */
public class OverseaWarehouseSkuAddResponse extends BaseResponse {
    public OverseaWarehouseSkuAddResponse(int code, String msg, Object data, OverseaWarehouseSkuAddRequest req) {
        super(code,msg,data,req);
    }
}
