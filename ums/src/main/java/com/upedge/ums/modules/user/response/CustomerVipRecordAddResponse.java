package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerVipRecordAddRequest;
/**
 * @author gx
 */
public class CustomerVipRecordAddResponse extends BaseResponse {
    public CustomerVipRecordAddResponse(int code, String msg, Object data, CustomerVipRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
