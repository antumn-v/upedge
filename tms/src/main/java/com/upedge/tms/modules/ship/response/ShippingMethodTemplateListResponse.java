package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateListRequest;

/**
 * @author author
 */
public class ShippingMethodTemplateListResponse extends BaseResponse {
    public ShippingMethodTemplateListResponse(int code, String msg, Object data, ShippingMethodTemplateListRequest req) {
        super(code,msg,data,req);
    }
}
