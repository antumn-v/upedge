package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AdminCurrencyRateListRequest;

/**
 * @author author
 */
public class AdminCurrencyRateListResponse extends BaseResponse {
    public AdminCurrencyRateListResponse(int code, String msg, Object data, AdminCurrencyRateListRequest req) {
        super(code,msg,data,req);
    }
}
