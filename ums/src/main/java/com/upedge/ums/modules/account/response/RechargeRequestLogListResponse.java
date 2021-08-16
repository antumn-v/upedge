package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeRequestLogListRequest;
/**
 * @author gx
 */
public class RechargeRequestLogListResponse extends BaseResponse {
    public RechargeRequestLogListResponse(int code, String msg, Object data,RechargeRequestLogListRequest req) {
        super(code,msg,data,req);
    }
}
