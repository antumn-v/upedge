package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderPackageImportLogAddRequest;
/**
 * @author gx
 */
public class OrderPackageImportLogAddResponse extends BaseResponse {
    public OrderPackageImportLogAddResponse(int code, String msg, Object data, OrderPackageImportLogAddRequest req) {
        super(code,msg,data,req);
    }
}
