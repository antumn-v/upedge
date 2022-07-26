package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderTrackingListRequest;
/**
 * @author gx
 */
public class PurchaseOrderTrackingListResponse extends BaseResponse {
    public PurchaseOrderTrackingListResponse(int code, String msg, Object data,PurchaseOrderTrackingListRequest req) {
        super(code,msg,data,req);
    }
}
