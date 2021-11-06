package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.entity.ShippingAttribute;
import com.upedge.tms.modules.ship.request.ShippingAttributeListRequest;

import java.util.List;

/**
 * @author author
 */
public class ShippingAttributeListResponse extends BaseResponse {
    public ShippingAttributeListResponse(int code, String msg, Object data) {
        super(code,msg,data);
    }
    public ShippingAttributeListResponse(int code, String msg, List<ShippingAttribute> results, ShippingAttributeListRequest req) {
        super(code,msg,results,req);
    }
}
