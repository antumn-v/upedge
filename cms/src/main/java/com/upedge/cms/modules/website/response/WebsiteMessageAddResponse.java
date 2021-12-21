package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteMessageAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteMessageAddResponse extends BaseResponse {
    public WebsiteMessageAddResponse(int code, String msg, Object data, WebsiteMessageAddRequest req) {
        super(code,msg,data,req);
    }
}
