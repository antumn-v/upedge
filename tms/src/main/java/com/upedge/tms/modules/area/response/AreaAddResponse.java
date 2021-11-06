package com.upedge.tms.modules.area.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.area.request.AreaAddRequest;

/**
 * @author author
 */
public class AreaAddResponse extends BaseResponse {
    public AreaAddResponse(int code, String msg, Object data, AreaAddRequest req) {
        super(code,msg,data,req);
    }
}
