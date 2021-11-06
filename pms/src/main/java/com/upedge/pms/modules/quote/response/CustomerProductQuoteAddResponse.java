package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteAddRequest;
/**
 * @author gx
 */
public class CustomerProductQuoteAddResponse extends BaseResponse {
    public CustomerProductQuoteAddResponse(int code, String msg, Object data, CustomerProductQuoteAddRequest req) {
        super(code,msg,data,req);
    }
}
