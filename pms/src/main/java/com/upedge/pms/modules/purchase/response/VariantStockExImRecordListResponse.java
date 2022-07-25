package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.VariantStockExImRecordListRequest;
/**
 * @author gx
 */
public class VariantStockExImRecordListResponse extends BaseResponse {
    public VariantStockExImRecordListResponse(int code, String msg, Object data,VariantStockExImRecordListRequest req) {
        super(code,msg,data,req);
    }
}
