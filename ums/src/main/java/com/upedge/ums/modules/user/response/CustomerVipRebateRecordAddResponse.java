package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerVipRebateRecordAddRequest;
/**
 * @author gx
 */
public class CustomerVipRebateRecordAddResponse extends BaseResponse {
    public CustomerVipRebateRecordAddResponse(int code, String msg, Object data, CustomerVipRebateRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
