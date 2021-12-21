package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteFaqInfoListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteFaqInfoListResponse extends BaseResponse {
    public WebsiteFaqInfoListResponse(int code, String msg, Object data,WebsiteFaqInfoListRequest req) {
        super(code,msg,data,req);
    }
}
