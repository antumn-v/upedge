package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageReplaceRecordListRequest;
/**
 * @author gx
 */
public class PackageReplaceRecordListResponse extends BaseResponse {
    public PackageReplaceRecordListResponse(int code, String msg, Object data,PackageReplaceRecordListRequest req) {
        super(code,msg,data,req);
    }
}
