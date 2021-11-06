package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.SupplierListRequest;

/**
 * @author author
 */
public class SupplierListResponse extends BaseResponse {
    public SupplierListResponse(int code, String msg, Object data, SupplierListRequest req) {
        super(code,msg,data,req);
    }
}
