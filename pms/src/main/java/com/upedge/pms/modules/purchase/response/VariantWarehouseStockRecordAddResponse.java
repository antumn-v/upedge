package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordAddRequest;
/**
 * @author gx
 */
public class VariantWarehouseStockRecordAddResponse extends BaseResponse {
    public VariantWarehouseStockRecordAddResponse(int code, String msg, Object data, VariantWarehouseStockRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
