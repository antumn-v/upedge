package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderPackageListRequest;
/**
 * @author gx
 */
public class OrderPackageListResponse extends BaseResponse {
    public OrderPackageListResponse(int code, String msg, Object data,OrderPackageListRequest req) {
        super(code,msg,data,req);
    }
}
