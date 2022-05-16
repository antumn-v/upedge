package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerComissionRecordListRequest;
/**
 * @author gx
 */
public class ManagerComissionRecordListResponse extends BaseResponse {
    public ManagerComissionRecordListResponse(int code, String msg, Object data,ManagerComissionRecordListRequest req) {
        super(code,msg,data,req);
    }
}
