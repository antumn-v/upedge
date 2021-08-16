package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationUserAddRequest;
/**
 * @author gx
 */
public class OrganizationUserAddResponse extends BaseResponse {
    public OrganizationUserAddResponse(int code, String msg, Object data, OrganizationUserAddRequest req) {
        super(code,msg,data,req);
    }
}
