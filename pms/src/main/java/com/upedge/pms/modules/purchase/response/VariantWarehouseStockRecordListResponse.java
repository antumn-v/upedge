package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockRecordListRequest;
/**
 * @author gx
 */
public class VariantWarehouseStockRecordListResponse extends BaseResponse {
    public VariantWarehouseStockRecordListResponse(int code, String msg, Object data,VariantWarehouseStockRecordListRequest req) {
        super(code,msg,data,req);
    }
}
