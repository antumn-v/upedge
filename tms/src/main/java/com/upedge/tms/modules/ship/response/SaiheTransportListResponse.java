package com.upedge.tms.modules.ship.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.request.SaiheTransportListRequest;

import java.util.List;

/**
 * @author author
 */
public class SaiheTransportListResponse extends BaseResponse {
    public SaiheTransportListResponse(int code, String msg, Object data) {
        super(code,msg,data);
    }

    public SaiheTransportListResponse(int code, String msg, List<SaiheTransport> data, SaiheTransportListRequest request) {
        super(code,msg,data,request);
    }
}
