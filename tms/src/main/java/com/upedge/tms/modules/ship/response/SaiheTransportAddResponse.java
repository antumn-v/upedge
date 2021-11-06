package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.request.SaiheTransportAddRequest;

/**
 * @author author
 */
public class SaiheTransportAddResponse extends BaseResponse {
    public SaiheTransportAddResponse(int code, String msg, Object data, SaiheTransportAddRequest req) {
        super(code,msg,data,req);
    }
}
