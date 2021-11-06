package com.upedge.pms.modules.inventory.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.inventory.request.ProductSaiheInventoryListRequest;

/**
 * @author Ï¦Îí
 */
public class ProductSaiheInventoryListResponse extends BaseResponse {
    public ProductSaiheInventoryListResponse(int code, String msg, Object data, ProductSaiheInventoryListRequest req) {
        super(code,msg,data,req);
    }
}
