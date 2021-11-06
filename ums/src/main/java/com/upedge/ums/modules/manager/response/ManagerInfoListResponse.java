package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerInfoListRequest;

/**
 * @author author
 */
public class ManagerInfoListResponse extends BaseResponse {
    public ManagerInfoListResponse(int code, String msg, Object data, ManagerInfoListRequest req) {
        super(code,msg,data,req);
    }
}
