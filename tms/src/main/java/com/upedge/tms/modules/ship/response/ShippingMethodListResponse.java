package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingMethodListRequest;

/**
 * @author author
 */
public class ShippingMethodListResponse extends BaseResponse {
    public ShippingMethodListResponse(int code, String msg, Object data, ShippingMethodListRequest req) {
        super(code,msg,data,req);
    }
}
