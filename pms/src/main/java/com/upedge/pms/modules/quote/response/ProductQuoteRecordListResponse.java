package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.ProductQuoteRecordListRequest;
/**
 * @author gx
 */
public class ProductQuoteRecordListResponse extends BaseResponse {
    public ProductQuoteRecordListResponse(int code, String msg, Object data,ProductQuoteRecordListRequest req) {
        super(code,msg,data,req);
    }
}
