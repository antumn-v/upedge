package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteFaqInfoAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteFaqInfoAddResponse extends BaseResponse {
    public WebsiteFaqInfoAddResponse(int code, String msg, Object data, WebsiteFaqInfoAddRequest req) {
        super(code,msg,data,req);
    }
}
