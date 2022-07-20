package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderAddRequest;
/**
 * @author gx
 */
public class PurchaseOrderAddResponse extends BaseResponse {
    public PurchaseOrderAddResponse(int code, String msg, Object data, PurchaseOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
