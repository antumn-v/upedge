package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchasePlanListRequest;
/**
 * @author gx
 */
public class PurchasePlanListResponse extends BaseResponse {
    public PurchasePlanListResponse(int code, String msg, Object data,PurchasePlanListRequest req) {
        super(code,msg,data,req);
    }
}
