package com.upedge.oms.modules.orderShippingUnit.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.orderShippingUnit.request.OrderShippingUnitListRequest;

/**
 * @author xwCui
 */
public class OrderShippingUnitListResponse extends BaseResponse {
    public OrderShippingUnitListResponse(int code, String msg, Object data, OrderShippingUnitListRequest req) {
        super(code,msg,data,req);
    }
}
