package com.upedge.pms.modules.supplier.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.supplier.request.SupplierListRequest;
/**
 * @author gx
 */
public class SupplierListResponse extends BaseResponse {
    public SupplierListResponse(int code, String msg, Object data,SupplierListRequest req) {
        super(code,msg,data,req);
    }
}
