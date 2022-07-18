package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchasePlanAddRequest;
/**
 * @author gx
 */
public class PurchasePlanAddResponse extends BaseResponse {
    public PurchasePlanAddResponse(int code, String msg, Object data, PurchasePlanAddRequest req) {
        super(code,msg,data,req);
    }
}
