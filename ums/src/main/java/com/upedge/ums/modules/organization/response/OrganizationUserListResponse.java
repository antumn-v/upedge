package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationUserListRequest;
/**
 * @author gx
 */
public class OrganizationUserListResponse extends BaseResponse {
    public OrganizationUserListResponse(int code, String msg, Object data,OrganizationUserListRequest req) {
        super(code,msg,data,req);
    }
}
