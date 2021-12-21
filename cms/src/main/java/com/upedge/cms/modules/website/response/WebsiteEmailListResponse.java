package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteEmailListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteEmailListResponse extends BaseResponse {
    public WebsiteEmailListResponse(int code, String msg, Object data,WebsiteEmailListRequest req) {
        super(code,msg,data,req);
    }
}
