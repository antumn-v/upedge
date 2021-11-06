package com.upedge.pms.modules.quote.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.quote.request.QuoteApplyItemListRequest;
/**
 * @author gx
 */
public class QuoteApplyItemListResponse extends BaseResponse {
    public QuoteApplyItemListResponse(int code, String msg, Object data,QuoteApplyItemListRequest req) {
        super(code,msg,data,req);
    }
}
