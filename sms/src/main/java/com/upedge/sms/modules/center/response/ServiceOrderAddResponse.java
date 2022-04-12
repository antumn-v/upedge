package com.upedge.sms.modules.center.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.center.request.ServiceOrderAddRequest;
/**
 * @author gx
 */
public class ServiceOrderAddResponse extends BaseResponse {
    public ServiceOrderAddResponse(int code, String msg, Object data, ServiceOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
