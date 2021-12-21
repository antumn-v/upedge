package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteFaqCateAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteFaqCateAddResponse extends BaseResponse {
    public WebsiteFaqCateAddResponse(int code, String msg, Object data, WebsiteFaqCateAddRequest req) {
        super(code,msg,data,req);
    }
}
