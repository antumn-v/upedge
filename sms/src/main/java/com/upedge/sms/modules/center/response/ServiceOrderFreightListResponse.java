package com.upedge.sms.modules.center.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.center.request.ServiceOrderFreightListRequest;
/**
 * @author gx
 */
public class ServiceOrderFreightListResponse extends BaseResponse {
    public ServiceOrderFreightListResponse(int code, String msg, Object data,ServiceOrderFreightListRequest req) {
        super(code,msg,data,req);
    }
}
