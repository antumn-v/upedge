package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderPackageAddRequest;
/**
 * @author gx
 */
public class OrderPackageAddResponse extends BaseResponse {
    public OrderPackageAddResponse(int code, String msg, Object data, OrderPackageAddRequest req) {
        super(code,msg,data,req);
    }
}
