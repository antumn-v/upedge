package com.upedge.oms.modules.rules.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.rules.request.OrderShipRuleListRequest;

/**
 * @author author
 */
public class OrderShipRuleListResponse extends BaseResponse {

    public OrderShipRuleListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public OrderShipRuleListResponse(int code, String msg, Object data, OrderShipRuleListRequest req) {
        super(code,msg,data,req);
    }
}
