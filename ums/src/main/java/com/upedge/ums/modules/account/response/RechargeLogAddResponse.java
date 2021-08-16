package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeLogAddRequest;
/**
 * @author gx
 */
public class RechargeLogAddResponse extends BaseResponse {
    public RechargeLogAddResponse(int code, String msg, Object data, RechargeLogAddRequest req) {
        super(code,msg,data,req);
    }
}
