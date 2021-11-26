package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingUnitAddRequest;
/**
 * @author gx
 */
public class ShippingUnitAddResponse extends BaseResponse {
    public ShippingUnitAddResponse(int code, String msg, Object data, ShippingUnitAddRequest req) {
        super(code,msg,data,req);
    }
}
