package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordAddRequest;
/**
 * @author gx
 */
public class AffiliateCommissionRecordAddResponse extends BaseResponse {
    public AffiliateCommissionRecordAddResponse(int code, String msg, Object data, AffiliateCommissionRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
