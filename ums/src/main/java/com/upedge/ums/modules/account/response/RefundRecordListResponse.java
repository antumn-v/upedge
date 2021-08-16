package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RefundRecordListRequest;
/**
 * @author gx
 */
public class RefundRecordListResponse extends BaseResponse {
    public RefundRecordListResponse(int code, String msg, Object data,RefundRecordListRequest req) {
        super(code,msg,data,req);
    }
}
