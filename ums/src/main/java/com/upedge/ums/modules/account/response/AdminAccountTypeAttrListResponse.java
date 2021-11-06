package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AdminAccountTypeAttrListRequest;

/**
 * @author author
 */
public class AdminAccountTypeAttrListResponse extends BaseResponse {
    public AdminAccountTypeAttrListResponse(int code, String msg, Object data, AdminAccountTypeAttrListRequest req) {
        super(code,msg,data,req);
    }
}
