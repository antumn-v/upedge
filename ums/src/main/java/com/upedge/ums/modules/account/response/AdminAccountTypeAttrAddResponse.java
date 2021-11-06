package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.AdminAccountTypeAttrAddRequest;

/**
 * @author author
 */
public class AdminAccountTypeAttrAddResponse extends BaseResponse {
    public AdminAccountTypeAttrAddResponse(int code, String msg, Object data, AdminAccountTypeAttrAddRequest req) {
        super(code,msg,data,req);
    }
}
