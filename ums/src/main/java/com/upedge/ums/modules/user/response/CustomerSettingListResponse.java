package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.CustomerSettingListRequest;
/**
 * @author gx
 */
public class CustomerSettingListResponse extends BaseResponse {
    public CustomerSettingListResponse(int code, String msg, Object data,CustomerSettingListRequest req) {
        super(code,msg,data,req);
    }
}
