package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.RefundRecordAddRequest;
/**
 * @author gx
 */
public class RefundRecordAddResponse extends BaseResponse {
    public RefundRecordAddResponse(int code, String msg, Object data, RefundRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
