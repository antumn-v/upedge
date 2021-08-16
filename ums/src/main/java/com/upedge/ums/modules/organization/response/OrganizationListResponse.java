package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationListRequest;
/**
 * @author gx
 */
public class OrganizationListResponse extends BaseResponse {
    public OrganizationListResponse(int code, String msg, Object data,OrganizationListRequest req) {
        super(code,msg,data,req);
    }
}
