package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoAddRequest;
/**
 * @author gx
 */
public class ProductPurchaseInfoAddResponse extends BaseResponse {
    public ProductPurchaseInfoAddResponse(int code, String msg, Object data, ProductPurchaseInfoAddRequest req) {
        super(code,msg,data,req);
    }
}
