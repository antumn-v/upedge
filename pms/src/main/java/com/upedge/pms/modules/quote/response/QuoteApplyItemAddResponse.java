package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.QuoteApplyItemAddRequest;
/**
 * @author gx
 */
public class QuoteApplyItemAddResponse extends BaseResponse {
    public QuoteApplyItemAddResponse(int code, String msg, Object data, QuoteApplyItemAddRequest req) {
        super(code,msg,data,req);
    }
}
