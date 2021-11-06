package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageOrderInfoListRequest;

/**
 * @author author
 */
public class PackageOrderInfoListResponse extends BaseResponse {
    public PackageOrderInfoListResponse(int code, String msg, Object data, PackageOrderInfoListRequest req) {
        super(code,msg,data,req);
    }
}
