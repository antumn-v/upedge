package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionWithdrawalListRequest;
/**
 * @author gx
 */
public class AffiliateCommissionWithdrawalListResponse extends BaseResponse {
    public AffiliateCommissionWithdrawalListResponse(int code, String msg, Object data,AffiliateCommissionWithdrawalListRequest req) {
        super(code,msg,data,req);
    }
}
