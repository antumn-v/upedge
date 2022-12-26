package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderPackageImportLogListRequest;
/**
 * @author gx
 */
public class OrderPackageImportLogListResponse extends BaseResponse {
    public OrderPackageImportLogListResponse(int code, String msg, Object data,OrderPackageImportLogListRequest req) {
        super(code,msg,data,req);
    }
}
