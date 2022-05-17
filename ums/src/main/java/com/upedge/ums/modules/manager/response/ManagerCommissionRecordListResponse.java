package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerCommissionRecordListRequest;
/**
 * @author gx
 */
public class ManagerCommissionRecordListResponse extends BaseResponse {
    public ManagerCommissionRecordListResponse(int code, String msg, Object data,ManagerCommissionRecordListRequest req) {
        super(code,msg,data,req);
    }
}
