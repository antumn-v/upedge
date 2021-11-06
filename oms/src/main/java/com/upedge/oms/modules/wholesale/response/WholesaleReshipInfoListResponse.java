package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleReshipInfoListRequest;

/**
 * @author author
 */
public class WholesaleReshipInfoListResponse extends BaseResponse {
    public WholesaleReshipInfoListResponse(int code, String msg, Object data, WholesaleReshipInfoListRequest req) {
        super(code,msg,data,req);
    }
}
