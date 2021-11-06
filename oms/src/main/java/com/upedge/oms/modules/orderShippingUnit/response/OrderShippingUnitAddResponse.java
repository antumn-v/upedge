package com.upedge.oms.modules.orderShippingUnit.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.orderShippingUnit.request.OrderShippingUnitAddRequest;

/**
 * @author xwCui
 */
public class OrderShippingUnitAddResponse extends BaseResponse {
    public OrderShippingUnitAddResponse(int code, String msg, Object data, OrderShippingUnitAddRequest req) {
        super(code,msg,data,req);
    }
}
