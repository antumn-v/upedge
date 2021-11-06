package com.upedge.oms.modules.rules.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.rules.request.OrderShipRuleAddRequest;

/**
 * @author author
 */
public class OrderShipRuleAddResponse extends BaseResponse {

    public OrderShipRuleAddResponse(int code, String msg) {
        super(code, msg);
    }

    public OrderShipRuleAddResponse(int code, String msg, Object data, OrderShipRuleAddRequest req) {
        super(code,msg,data,req);
    }
}
