package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteBlogCommentAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteBlogCommentAddResponse extends BaseResponse {
    public WebsiteBlogCommentAddResponse(int code, String msg, Object data, WebsiteBlogCommentAddRequest req) {
        super(code,msg,data,req);
    }
}
