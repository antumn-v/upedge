package com.upedge.sms.modules.center.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.center.request.ServiceOrderListRequest;
/**
 * @author gx
 */
public class ServiceOrderListResponse extends BaseResponse {
    public ServiceOrderListResponse(int code, String msg, Object data,ServiceOrderListRequest req) {
        super(code,msg,data,req);
    }
}
