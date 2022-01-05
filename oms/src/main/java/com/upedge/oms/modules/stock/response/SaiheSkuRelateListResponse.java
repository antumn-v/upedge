package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.SaiheSkuRelateListRequest;
/**
 * @author gx
 */
public class SaiheSkuRelateListResponse extends BaseResponse {
    public SaiheSkuRelateListResponse(int code, String msg, Object data,SaiheSkuRelateListRequest req) {
        super(code,msg,data,req);
    }
}
