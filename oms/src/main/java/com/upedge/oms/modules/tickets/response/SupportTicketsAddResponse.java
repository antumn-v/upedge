package com.upedge.oms.modules.tickets.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.tickets.request.SupportTicketsAddRequest;

/**
 * @author author
 */
public class SupportTicketsAddResponse extends BaseResponse {
    public SupportTicketsAddResponse(int code, String msg, Object data, SupportTicketsAddRequest req) {
        super(code,msg,data,req);
    }
}
