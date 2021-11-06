package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingUnitListRequest;

/**
 * @author author
 */
public class ShippingUnitListResponse extends BaseResponse {
    public ShippingUnitListResponse(int code, String msg, Object data, ShippingUnitListRequest req) {
        super(code,msg,data,req);
    }
}
