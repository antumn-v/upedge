package com.upedge.pms.modules.supplier.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.supplier.request.SupplierAddRequest;
/**
 * @author gx
 */
public class SupplierAddResponse extends BaseResponse {
    public SupplierAddResponse(int code, String msg, Object data, SupplierAddRequest req) {
        super(code,msg,data,req);
    }
}
