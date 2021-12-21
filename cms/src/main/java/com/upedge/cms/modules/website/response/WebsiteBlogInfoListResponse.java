package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteBlogInfoListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteBlogInfoListResponse extends BaseResponse {
    public WebsiteBlogInfoListResponse(int code, String msg, Object data,WebsiteBlogInfoListRequest req) {
        super(code,msg,data,req);
    }
}
