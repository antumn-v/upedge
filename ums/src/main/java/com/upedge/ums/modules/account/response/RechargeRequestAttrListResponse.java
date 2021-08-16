package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeRequestAttrListRequest;
/**
 * @author gx
 */
public class RechargeRequestAttrListResponse extends BaseResponse {
    public RechargeRequestAttrListResponse(int code, String msg, Object data,RechargeRequestAttrListRequest req) {
        super(code,msg,data,req);
    }
}
