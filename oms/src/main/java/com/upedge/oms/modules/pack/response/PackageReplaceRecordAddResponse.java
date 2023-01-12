package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageReplaceRecordAddRequest;
/**
 * @author gx
 */
public class PackageReplaceRecordAddResponse extends BaseResponse {
    public PackageReplaceRecordAddResponse(int code, String msg, Object data, PackageReplaceRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
