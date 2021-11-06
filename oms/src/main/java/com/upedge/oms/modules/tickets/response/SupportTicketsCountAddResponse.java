package com.upedge.oms.modules.tickets.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.tickets.request.SupportTicketsCountAddRequest;

/**
 * @author author
 */
public class SupportTicketsCountAddResponse extends BaseResponse {
    public SupportTicketsCountAddResponse(int code, String msg, Object data, SupportTicketsCountAddRequest req) {
        super(code,msg,data,req);
    }
}
