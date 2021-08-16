package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeLogListRequest;
/**
 * @author gx
 */
public class RechargeLogListResponse extends BaseResponse {
    public RechargeLogListResponse(int code, String msg, Object data,RechargeLogListRequest req) {
        super(code,msg,data,req);
    }
}
