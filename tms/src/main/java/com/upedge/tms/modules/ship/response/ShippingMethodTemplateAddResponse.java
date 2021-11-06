package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateAddRequest;

/**
 * @author author
 */
public class ShippingMethodTemplateAddResponse extends BaseResponse {
    public ShippingMethodTemplateAddResponse(int code, String msg, Object data, ShippingMethodTemplateAddRequest req) {
        super(code,msg,data,req);
    }

    public ShippingMethodTemplateAddResponse(int code, String message) {
        super(code,message);
    }
}
