package com.upedge.oms.modules.tickets.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.tickets.request.SupportTicketsMessageAddRequest;

/**
 * @author author
 */
public class SupportTicketsMessageAddResponse extends BaseResponse {
    public SupportTicketsMessageAddResponse(int code, String msg, Object data, SupportTicketsMessageAddRequest req) {
        super(code,msg,data,req);
    }
}
