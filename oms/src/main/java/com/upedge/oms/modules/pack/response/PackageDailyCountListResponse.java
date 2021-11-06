package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageDailyCountListRequest;

/**
 * @author gx
 */
public class PackageDailyCountListResponse extends BaseResponse {
    public PackageDailyCountListResponse(int code, String msg, Object data, PackageDailyCountListRequest req) {
        super(code,msg,data,req);
    }
}
