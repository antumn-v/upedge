package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerVipRecordListRequest;
/**
 * @author gx
 */
public class CustomerVipRecordListResponse extends BaseResponse {
    public CustomerVipRecordListResponse(int code, String msg, Object data,CustomerVipRecordListRequest req) {
        super(code,msg,data,req);
    }
}
