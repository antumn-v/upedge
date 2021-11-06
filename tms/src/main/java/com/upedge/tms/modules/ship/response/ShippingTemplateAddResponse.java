package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingTemplateAddRequest;

/**
 * @author author
 */
public class ShippingTemplateAddResponse extends BaseResponse {
    public ShippingTemplateAddResponse(int code, String msg, Object data, ShippingTemplateAddRequest req) {
        super(code,msg,data,req);
    }
}
