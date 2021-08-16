package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationMenuAddRequest;
/**
 * @author gx
 */
public class OrganizationMenuAddResponse extends BaseResponse {
    public OrganizationMenuAddResponse(int code, String msg, Object data, OrganizationMenuAddRequest req) {
        super(code,msg,data,req);
    }
}
