package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalAddRequest;

/**
 * @author author
 */
public class AffiliateCommissionWithdrawalAddResponse extends BaseResponse {

    public AffiliateCommissionWithdrawalAddResponse(int code, String msg) {
        super(code, msg);
    }

    public AffiliateCommissionWithdrawalAddResponse(int code, String msg, Object data, AffiliateCommissionWithdrawalAddRequest req) {
        super(code,msg,data,req);
    }
}
