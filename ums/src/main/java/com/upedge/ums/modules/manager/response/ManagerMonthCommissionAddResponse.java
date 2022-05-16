package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerMonthCommissionAddRequest;
/**
 * @author gx
 */
public class ManagerMonthCommissionAddResponse extends BaseResponse {
    public ManagerMonthCommissionAddResponse(int code, String msg, Object data, ManagerMonthCommissionAddRequest req) {
        super(code,msg,data,req);
    }
}
