package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingAttributeAddRequest;

/**
 * @author author
 */
public class ShippingAttributeAddResponse extends BaseResponse {
    public ShippingAttributeAddResponse(int code, String msg, Object data, ShippingAttributeAddRequest req) {
        super(code,msg,data,req);
    }
}
