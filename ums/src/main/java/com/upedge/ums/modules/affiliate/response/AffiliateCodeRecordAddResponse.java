package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateCodeRecordAddRequest;
/**
 * @author gx
 */
public class AffiliateCodeRecordAddResponse extends BaseResponse {
    public AffiliateCodeRecordAddResponse(int code, String msg, Object data, AffiliateCodeRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
