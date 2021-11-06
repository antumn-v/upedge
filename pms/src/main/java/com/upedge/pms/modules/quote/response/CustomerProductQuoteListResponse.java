package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.CustomerProductQuoteListRequest;
/**
 * @author gx
 */
public class CustomerProductQuoteListResponse extends BaseResponse {
    public CustomerProductQuoteListResponse(int code, String msg, Object data,CustomerProductQuoteListRequest req) {
        super(code,msg,data,req);
    }
}
