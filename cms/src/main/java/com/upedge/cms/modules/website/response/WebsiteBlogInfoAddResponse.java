package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteBlogInfoAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteBlogInfoAddResponse extends BaseResponse {
    public WebsiteBlogInfoAddResponse(int code, String msg, Object data, WebsiteBlogInfoAddRequest req) {
        super(code,msg,data,req);
    }
}
