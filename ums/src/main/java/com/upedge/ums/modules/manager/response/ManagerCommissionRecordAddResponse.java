package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerCommissionRecordAddRequest;
/**
 * @author gx
 */
public class ManagerCommissionRecordAddResponse extends BaseResponse {
    public ManagerCommissionRecordAddResponse(int code, String msg, Object data, ManagerCommissionRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
