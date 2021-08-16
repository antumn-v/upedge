package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RechargeRecordListRequest;
/**
 * @author gx
 */
public class RechargeRecordListResponse extends BaseResponse {
    public RechargeRecordListResponse(int code, String msg, Object data,RechargeRecordListRequest req) {
        super(code,msg,data,req);
    }
}
