package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationMenuListRequest;
/**
 * @author gx
 */
public class OrganizationMenuListResponse extends BaseResponse {
    public OrganizationMenuListResponse(int code, String msg, Object data,OrganizationMenuListRequest req) {
        super(code,msg,data,req);
    }
}
