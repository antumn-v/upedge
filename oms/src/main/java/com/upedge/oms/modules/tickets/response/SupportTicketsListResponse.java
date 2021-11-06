package com.upedge.oms.modules.tickets.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.tickets.request.SupportTicketsListRequest;

/**
 * @author author
 */
public class SupportTicketsListResponse extends BaseResponse {
    public SupportTicketsListResponse(int code, String msg, Object data, SupportTicketsListRequest req) {
        super(code,msg,data,req);
    }
}
