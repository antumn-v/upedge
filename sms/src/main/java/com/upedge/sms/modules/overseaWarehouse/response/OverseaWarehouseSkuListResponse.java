package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseSkuListRequest;
/**
 * @author gx
 */
public class OverseaWarehouseSkuListResponse extends BaseResponse {
    public OverseaWarehouseSkuListResponse(int code, String msg, Object data,OverseaWarehouseSkuListRequest req) {
        super(code,msg,data,req);
    }
}
