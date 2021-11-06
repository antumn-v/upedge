package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageTrackingListRequest;

/**
 * @author author
 */
public class PackageTrackingListResponse extends BaseResponse {
    public PackageTrackingListResponse(int code, String msg, Object data, PackageTrackingListRequest req) {
        super(code,msg,data,req);
    }
}
