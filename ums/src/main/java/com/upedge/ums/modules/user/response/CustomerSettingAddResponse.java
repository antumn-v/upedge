package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerSettingAddRequest;
/**
 * @author gx
 */
public class CustomerSettingAddResponse extends BaseResponse {
    public CustomerSettingAddResponse(int code, String msg, Object data, CustomerSettingAddRequest req) {
        super(code,msg,data,req);
    }
}
