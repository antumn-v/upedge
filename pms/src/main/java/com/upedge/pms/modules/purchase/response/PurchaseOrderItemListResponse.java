package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderItemListRequest;
/**
 * @author gx
 */
public class PurchaseOrderItemListResponse extends BaseResponse {
    public PurchaseOrderItemListResponse(int code, String msg, Object data,PurchaseOrderItemListRequest req) {
        super(code,msg,data,req);
    }
}
