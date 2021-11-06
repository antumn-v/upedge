package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerInfoAddRequest;

/**
 * @author author
 */
public class ManagerInfoAddResponse extends BaseResponse {
    public ManagerInfoAddResponse(int code, String msg, Object data, ManagerInfoAddRequest req) {
        super(code,msg,data,req);
    }
}
