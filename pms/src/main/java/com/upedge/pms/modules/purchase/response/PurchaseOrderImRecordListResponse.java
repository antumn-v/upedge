package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderImRecordListRequest;
/**
 * @author gx
 */
public class PurchaseOrderImRecordListResponse extends BaseResponse {
    public PurchaseOrderImRecordListResponse(int code, String msg, Object data,PurchaseOrderImRecordListRequest req) {
        super(code,msg,data,req);
    }
}
