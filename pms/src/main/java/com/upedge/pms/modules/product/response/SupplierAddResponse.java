package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.SupplierAddRequest;

/**
 * @author author
 */
public class SupplierAddResponse extends BaseResponse {
    public SupplierAddResponse(int code, String msg, Object data, SupplierAddRequest req) {
        super(code,msg,data,req);
    }
}
