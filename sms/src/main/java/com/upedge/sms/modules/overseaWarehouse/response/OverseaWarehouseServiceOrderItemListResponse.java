package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemListRequest;
/**
 * @author gx
 */
public class OverseaWarehouseServiceOrderItemListResponse extends BaseResponse {
    public OverseaWarehouseServiceOrderItemListResponse(int code, String msg, Object data,OverseaWarehouseServiceOrderItemListRequest req) {
        super(code,msg,data,req);
    }
}
