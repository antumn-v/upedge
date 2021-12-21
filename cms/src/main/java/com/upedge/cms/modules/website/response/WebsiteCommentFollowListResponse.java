package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteCommentFollowListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteCommentFollowListResponse extends BaseResponse {
    public WebsiteCommentFollowListResponse(int code, String msg, Object data,WebsiteCommentFollowListRequest req) {
        super(code,msg,data,req);
    }
}
