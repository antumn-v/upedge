package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteCommentFollowAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteCommentFollowAddResponse extends BaseResponse {
    public WebsiteCommentFollowAddResponse(int code, String msg, Object data, WebsiteCommentFollowAddRequest req) {
        super(code,msg,data,req);
    }
}
