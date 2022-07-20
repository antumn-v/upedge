package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
/**
 * @author gx
 */
public class PurchaseOrderListResponse extends BaseResponse {
    public PurchaseOrderListResponse(int code, String msg, Object data,PurchaseOrderListRequest req) {
        super(code,msg,data,req);
    }
}
