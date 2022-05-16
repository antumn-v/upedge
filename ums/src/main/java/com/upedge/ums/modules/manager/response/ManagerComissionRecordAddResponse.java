package com.upedge.ums.modules.manager.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.manager.request.ManagerComissionRecordAddRequest;
/**
 * @author gx
 */
public class ManagerComissionRecordAddResponse extends BaseResponse {
    public ManagerComissionRecordAddResponse(int code, String msg, Object data, ManagerComissionRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
