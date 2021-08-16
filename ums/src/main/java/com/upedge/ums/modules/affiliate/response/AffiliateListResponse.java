package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateListRequest;
/**
 * @author gx
 */
public class AffiliateListResponse extends BaseResponse {
    public AffiliateListResponse(int code, String msg, Object data,AffiliateListRequest req) {
        super(code,msg,data,req);
    }
}
