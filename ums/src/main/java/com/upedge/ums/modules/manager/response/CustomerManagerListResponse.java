package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.CustomerManagerListRequest;
/**
 * @author gx
 */
public class CustomerManagerListResponse extends BaseResponse {
    public CustomerManagerListResponse(int code, String msg, Object data,CustomerManagerListRequest req) {
        super(code,msg,data,req);
    }
}
