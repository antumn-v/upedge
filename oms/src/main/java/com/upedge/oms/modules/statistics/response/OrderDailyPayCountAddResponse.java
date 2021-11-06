package com.upedge.oms.modules.statistics.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.statistics.request.OrderDailyPayCountAddRequest;

/**
 * @author author
 */
public class OrderDailyPayCountAddResponse extends BaseResponse {
    public OrderDailyPayCountAddResponse(int code, String msg, Object data, OrderDailyPayCountAddRequest req) {
        super(code,msg,data,req);
    }
}
