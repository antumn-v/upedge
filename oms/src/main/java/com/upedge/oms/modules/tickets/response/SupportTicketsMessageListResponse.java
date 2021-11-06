package com.upedge.oms.modules.tickets.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.tickets.request.SupportTicketsMessageListRequest;

/**
 * @author author
 */
public class SupportTicketsMessageListResponse extends BaseResponse {
    public SupportTicketsMessageListResponse(int code, String msg, Object data, SupportTicketsMessageListRequest req) {
        super(code,msg,data,req);
    }
    public SupportTicketsMessageListResponse(int code, String msg){
        super(code,msg);
    }
}
