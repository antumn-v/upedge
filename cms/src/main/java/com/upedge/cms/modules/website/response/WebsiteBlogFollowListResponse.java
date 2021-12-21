package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteBlogFollowListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteBlogFollowListResponse extends BaseResponse {
    public WebsiteBlogFollowListResponse(int code, String msg, Object data,WebsiteBlogFollowListRequest req) {
        super(code,msg,data,req);
    }
}
