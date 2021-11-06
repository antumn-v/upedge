package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageInfoListRequest;

/**
 * @author author
 */
public class PackageInfoListResponse extends BaseResponse {
    public PackageInfoListResponse(int code, String msg, Object data, PackageInfoListRequest req) {
        super(code,msg,data,req);
    }
}
