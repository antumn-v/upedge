package com.upedge.sms.modules.center.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.center.request.ServiceOrderFreightAddRequest;
/**
 * @author gx
 */
public class ServiceOrderFreightAddResponse extends BaseResponse {
    public ServiceOrderFreightAddResponse(int code, String msg, Object data, ServiceOrderFreightAddRequest req) {
        super(code,msg,data,req);
    }
}
