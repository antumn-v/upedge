package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeRequestAttrAddRequest;
/**
 * @author gx
 */
public class RechargeRequestAttrAddResponse extends BaseResponse {
    public RechargeRequestAttrAddResponse(int code, String msg, Object data, RechargeRequestAttrAddRequest req) {
        super(code,msg,data,req);
    }
}
