package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderImItemListRequest;
/**
 * @author gx
 */
public class PurchaseOrderImItemListResponse extends BaseResponse {
    public PurchaseOrderImItemListResponse(int code, String msg, Object data,PurchaseOrderImItemListRequest req) {
        super(code,msg,data,req);
    }
}
