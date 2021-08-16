package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalAddRequest;
/**
 * @author gx
 */
public class AffiliateCommissionWithdrawalAddResponse extends BaseResponse {
    public AffiliateCommissionWithdrawalAddResponse(int code, String msg, Object data, AffiliateCommissionWithdrawalAddRequest req) {
        super(code,msg,data,req);
    }
}
