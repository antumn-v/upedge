package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AdminCurrencyRateAddRequest;

/**
 * @author author
 */
public class AdminCurrencyRateAddResponse extends BaseResponse {
    public AdminCurrencyRateAddResponse(int code, String msg, Object data, AdminCurrencyRateAddRequest req) {
        super(code,msg,data,req);
    }
    public AdminCurrencyRateAddResponse(int code, String msg){
        super(code,msg);
    }
}
