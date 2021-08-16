package com.upedge.ums.modules.user.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.user.request.UserInfoListRequest;
/**
 * @author gx
 */
public class UserInfoListResponse extends BaseResponse {
    public UserInfoListResponse(int code, String msg, Object data,UserInfoListRequest req) {
        super(code,msg,data,req);
    }
}
