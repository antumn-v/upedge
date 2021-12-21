package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteRemarkAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteRemarkAddResponse extends BaseResponse {
    public WebsiteRemarkAddResponse(int code, String msg, Object data, WebsiteRemarkAddRequest req) {
        super(code,msg,data,req);
    }
}
