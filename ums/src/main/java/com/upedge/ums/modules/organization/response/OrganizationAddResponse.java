package com.upedge.ums.modules.organization.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.organization.request.OrganizationAddRequest;
/**
 * @author gx
 */
public class OrganizationAddResponse extends BaseResponse {
    public OrganizationAddResponse(int code, String msg, Object data, OrganizationAddRequest req) {
        super(code,msg,data,req);
    }
}
