package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockListRequest;
/**
 * @author gx
 */
public class VariantWarehouseStockListResponse extends BaseResponse {
    public VariantWarehouseStockListResponse(int code, String msg, Object data,VariantWarehouseStockListRequest req) {
        super(code,msg,data,req);
    }
}
