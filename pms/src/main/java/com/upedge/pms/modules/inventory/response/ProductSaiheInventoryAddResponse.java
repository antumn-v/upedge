package com.upedge.pms.modules.inventory.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.inventory.request.ProductSaiheInventoryAddRequest;

/**
 * @author Ï¦Îí
 */
public class ProductSaiheInventoryAddResponse extends BaseResponse {
    public ProductSaiheInventoryAddResponse(int code, String msg, Object data, ProductSaiheInventoryAddRequest req) {
        super(code,msg,data,req);
    }
}
