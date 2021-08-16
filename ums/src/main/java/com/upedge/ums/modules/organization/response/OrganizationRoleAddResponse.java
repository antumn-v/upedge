package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationRoleAddRequest;
/**
 * @author gx
 */
public class OrganizationRoleAddResponse extends BaseResponse {
    public OrganizationRoleAddResponse(int code, String msg, Object data, OrganizationRoleAddRequest req) {
        super(code,msg,data,req);
    }
}
