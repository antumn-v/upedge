package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderPackageBackupListRequest;
/**
 * @author gx
 */
public class OrderPackageBackupListResponse extends BaseResponse {
    public OrderPackageBackupListResponse(int code, String msg, Object data,OrderPackageBackupListRequest req) {
        super(code,msg,data,req);
    }
}
