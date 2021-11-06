package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.QuoteApplyAddRequest;
/**
 * @author gx
 */
public class QuoteApplyAddResponse extends BaseResponse {
    public QuoteApplyAddResponse(int code, String msg, Object data, QuoteApplyAddRequest req) {
        super(code,msg,data,req);
    }
}
