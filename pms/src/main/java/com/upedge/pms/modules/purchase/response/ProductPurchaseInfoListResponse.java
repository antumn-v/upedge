package com.upedge.pms.modules.purchase.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoListRequest;
/**
 * @author gx
 */
public class ProductPurchaseInfoListResponse extends BaseResponse {
    public ProductPurchaseInfoListResponse(int code, String msg, Object data,ProductPurchaseInfoListRequest req) {
        super(code,msg,data,req);
    }
}
