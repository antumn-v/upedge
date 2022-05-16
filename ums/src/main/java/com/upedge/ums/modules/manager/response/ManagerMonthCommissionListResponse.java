package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerMonthCommissionListRequest;
/**
 * @author gx
 */
public class ManagerMonthCommissionListResponse extends BaseResponse {
    public ManagerMonthCommissionListResponse(int code, String msg, Object data,ManagerMonthCommissionListRequest req) {
        super(code,msg,data,req);
    }
}
