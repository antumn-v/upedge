package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.VariantStockExImRecordAddRequest;
/**
 * @author gx
 */
public class VariantStockExImRecordAddResponse extends BaseResponse {
    public VariantStockExImRecordAddResponse(int code, String msg, Object data, VariantStockExImRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
