package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderTrackingAddRequest;
/**
 * @author gx
 */
public class PurchaseOrderTrackingAddResponse extends BaseResponse {
    public PurchaseOrderTrackingAddResponse(int code, String msg, Object data, PurchaseOrderTrackingAddRequest req) {
        super(code,msg,data,req);
    }
}
