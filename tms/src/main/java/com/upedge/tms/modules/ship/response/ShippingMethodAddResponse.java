package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingMethodAddRequest;

/**
 * @author author
 */
public class ShippingMethodAddResponse extends BaseResponse {
    public ShippingMethodAddResponse(int code, String msg, Object data, ShippingMethodAddRequest req) {
        super(code,msg,data,req);
    }
}
