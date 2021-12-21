package com.upedge.cms.modules.website.response;

import com.upedge.cms.modules.website.request.WebsiteFaqCateListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class WebsiteFaqCateListResponse extends BaseResponse {
    public WebsiteFaqCateListResponse(int code, String msg, Object data,WebsiteFaqCateListRequest req) {
        super(code,msg,data,req);
    }
}
