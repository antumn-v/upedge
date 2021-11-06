package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageInfoListQueryRequest;

public class PackageInfoListQueryResponse extends BaseResponse {
    public PackageInfoListQueryResponse(int code, String msg, Object data, PackageInfoListQueryRequest req) {
        super(code,msg,data,req);
    }
}
