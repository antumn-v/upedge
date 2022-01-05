package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.SaiheSkuRelateAddRequest;
/**
 * @author gx
 */
public class SaiheSkuRelateAddResponse extends BaseResponse {
    public SaiheSkuRelateAddResponse(int code, String msg, Object data, SaiheSkuRelateAddRequest req) {
        super(code,msg,data,req);
    }
}
