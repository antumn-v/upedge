package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteBlogFollowAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteBlogFollowAddResponse extends BaseResponse {
    public WebsiteBlogFollowAddResponse(int code, String msg, Object data, WebsiteBlogFollowAddRequest req) {
        super(code,msg,data,req);
    }
}
