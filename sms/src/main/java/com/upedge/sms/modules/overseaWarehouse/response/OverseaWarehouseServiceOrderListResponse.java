package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderListRequest;
/**
 * @author gx
 */
public class OverseaWarehouseServiceOrderListResponse extends BaseResponse {
    public OverseaWarehouseServiceOrderListResponse(int code, String msg, Object data,OverseaWarehouseServiceOrderListRequest req) {
        super(code,msg,data,req);
    }
}
