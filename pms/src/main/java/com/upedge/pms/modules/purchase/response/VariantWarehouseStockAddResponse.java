package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockAddRequest;
/**
 * @author gx
 */
public class VariantWarehouseStockAddResponse extends BaseResponse {
    public VariantWarehouseStockAddResponse(int code, String msg, Object data, VariantWarehouseStockAddRequest req) {
        super(code,msg,data,req);
    }
}
