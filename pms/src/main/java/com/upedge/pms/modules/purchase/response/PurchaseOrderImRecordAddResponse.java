package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderImRecordAddRequest;
/**
 * @author gx
 */
public class PurchaseOrderImRecordAddResponse extends BaseResponse {
    public PurchaseOrderImRecordAddResponse(int code, String msg, Object data, PurchaseOrderImRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
