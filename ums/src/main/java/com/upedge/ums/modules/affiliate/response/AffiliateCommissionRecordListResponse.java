package com.upedge.ums.modules.affiliate.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.affiliate.request.AffiliateCommissionRecordListRequest;

/**
 * @author author
 */
public class AffiliateCommissionRecordListResponse extends BaseResponse {

    public AffiliateCommissionRecordListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public AffiliateCommissionRecordListResponse(int code, String msg, Object data, AffiliateCommissionRecordListRequest req) {
        super(code,msg,data,req);
    }
}
