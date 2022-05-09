package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateCodeRecordListRequest;
/**
 * @author gx
 */
public class AffiliateCodeRecordListResponse extends BaseResponse {
    public AffiliateCodeRecordListResponse(int code, String msg, Object data,AffiliateCodeRecordListRequest req) {
        super(code,msg,data,req);
    }
}
