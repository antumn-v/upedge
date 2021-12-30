package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.ProductQuoteRecordAddRequest;
/**
 * @author gx
 */
public class ProductQuoteRecordAddResponse extends BaseResponse {
    public ProductQuoteRecordAddResponse(int code, String msg, Object data, ProductQuoteRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
