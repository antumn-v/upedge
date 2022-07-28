package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderImItemAddRequest;
/**
 * @author gx
 */
public class PurchaseOrderImItemAddResponse extends BaseResponse {
    public PurchaseOrderImItemAddResponse(int code, String msg, Object data, PurchaseOrderImItemAddRequest req) {
        super(code,msg,data,req);
    }
}
