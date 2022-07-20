package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemAddRequest;
/**
 * @author gx
 */
public class PurchaseOrderItemAddResponse extends BaseResponse {
    public PurchaseOrderItemAddResponse(int code, String msg, Object data, PurchaseOrderItemAddRequest req) {
        super(code,msg,data,req);
    }
}
