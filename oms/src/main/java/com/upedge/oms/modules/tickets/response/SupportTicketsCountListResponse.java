package com.upedge.oms.modules.tickets.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.tickets.request.SupportTicketsCountListRequest;

/**
 * @author author
 */
public class SupportTicketsCountListResponse extends BaseResponse {
    public SupportTicketsCountListResponse(int code, String msg, Object data, SupportTicketsCountListRequest req) {
        super(code,msg,data,req);
    }
}
