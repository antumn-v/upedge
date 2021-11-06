package com.upedge.tms.modules.area.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.area.request.AreaListRequest;

/**
 * @author author
 */
public class AreaListResponse extends BaseResponse {
    public AreaListResponse(int code, String msg, Object data, AreaListRequest req) {
        super(code,msg,data,req);
    }
}
