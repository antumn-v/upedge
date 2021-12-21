package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteRemarkListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteRemarkListResponse extends BaseResponse {
    public WebsiteRemarkListResponse(int code, String msg, Object data,WebsiteRemarkListRequest req) {
        super(code,msg,data,req);
    }
}
