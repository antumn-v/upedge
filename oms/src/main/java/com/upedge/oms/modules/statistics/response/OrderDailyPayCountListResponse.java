package com.upedge.oms.modules.statistics.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.statistics.request.OrderDailyPayCountListRequest;

/**
 * @author author
 */
public class OrderDailyPayCountListResponse extends BaseResponse {
    public OrderDailyPayCountListResponse(int code, String msg, Object data, OrderDailyPayCountListRequest req) {
        super(code,msg,data,req);
    }
}
