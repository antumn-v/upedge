package com.upedge.cms.modules.notice.response;

import com.upedge.cms.modules.notice.request.AppNoticeListRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class AppNoticeListResponse extends BaseResponse {
    public AppNoticeListResponse(int code, String msg, Object data,AppNoticeListRequest req) {
        super(code,msg,data,req);
    }
}
