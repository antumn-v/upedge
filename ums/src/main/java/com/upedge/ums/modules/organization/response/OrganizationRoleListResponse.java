package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationRoleListRequest;
/**
 * @author gx
 */
public class OrganizationRoleListResponse extends BaseResponse {
    public OrganizationRoleListResponse(int code, String msg, Object data,OrganizationRoleListRequest req) {
        super(code,msg,data,req);
    }
}
