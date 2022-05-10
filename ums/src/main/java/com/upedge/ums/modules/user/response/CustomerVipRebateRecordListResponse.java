package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerVipRebateRecordListRequest;
/**
 * @author gx
 */
public class CustomerVipRebateRecordListResponse extends BaseResponse {
    public CustomerVipRebateRecordListResponse(int code, String msg, Object data,CustomerVipRebateRecordListRequest req) {
        super(code,msg,data,req);
    }
}
