package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingTemplateListRequest;

/**
 * @author author
 */
public class ShippingTemplateListResponse extends BaseResponse {
    public ShippingTemplateListResponse(int code, String msg, Object data) {
        super(code,msg,data);
    }
    public ShippingTemplateListResponse(int code, String msg, Object data, ShippingTemplateListRequest req) {
        super(code,msg,data,req);
    }
}
