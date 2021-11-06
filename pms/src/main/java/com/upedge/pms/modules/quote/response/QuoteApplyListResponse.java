package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.QuoteApplyListRequest;
/**
 * @author gx
 */
public class QuoteApplyListResponse extends BaseResponse {
    public QuoteApplyListResponse(int code, String msg, Object data,QuoteApplyListRequest req) {
        super(code,msg,data,req);
    }
}
