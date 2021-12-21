package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteMessageListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteMessageListResponse extends BaseResponse {
    public WebsiteMessageListResponse(int code, String msg, Object data,WebsiteMessageListRequest req) {
        super(code,msg,data,req);
    }
}
