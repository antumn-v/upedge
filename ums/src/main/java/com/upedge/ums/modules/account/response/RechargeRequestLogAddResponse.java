package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeRequestLogAddRequest;
/**
 * @author gx
 */
public class RechargeRequestLogAddResponse extends BaseResponse {
    public RechargeRequestLogAddResponse(int code, String msg, Object data, RechargeRequestLogAddRequest req) {
        super(code,msg,data,req);
    }
}
