package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageDailyCountAddRequest;

/**
 * @author gx
 */
public class PackageDailyCountAddResponse extends BaseResponse {
    public PackageDailyCountAddResponse(int code, String msg, Object data, PackageDailyCountAddRequest req) {
        super(code,msg,data,req);
    }
}
