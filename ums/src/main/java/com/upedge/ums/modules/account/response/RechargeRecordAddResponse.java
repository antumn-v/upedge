package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeRecordAddRequest;
/**
 * @author gx
 */
public class RechargeRecordAddResponse extends BaseResponse {
    public RechargeRecordAddResponse(int code, String msg, Object data, RechargeRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
