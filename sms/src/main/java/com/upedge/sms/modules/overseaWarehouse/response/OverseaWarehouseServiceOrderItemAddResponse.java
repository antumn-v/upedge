package com.upedge.sms.modules.overseaWarehouse.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemAddRequest;
/**
 * @author gx
 */
public class OverseaWarehouseServiceOrderItemAddResponse extends BaseResponse {
    public OverseaWarehouseServiceOrderItemAddResponse(int code, String msg, Object data, OverseaWarehouseServiceOrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
