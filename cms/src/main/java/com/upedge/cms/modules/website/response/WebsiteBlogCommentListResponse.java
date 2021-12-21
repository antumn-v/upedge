package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteBlogCommentListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteBlogCommentListResponse extends BaseResponse {
    public WebsiteBlogCommentListResponse(int code, String msg, Object data,WebsiteBlogCommentListRequest req) {
        super(code,msg,data,req);
    }
}
