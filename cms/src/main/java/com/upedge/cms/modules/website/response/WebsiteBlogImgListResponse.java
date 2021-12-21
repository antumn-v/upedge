package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteBlogImgListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteBlogImgListResponse extends BaseResponse {
    public WebsiteBlogImgListResponse(int code, String msg, Object data,WebsiteBlogImgListRequest req) {
        super(code,msg,data,req);
    }
}
