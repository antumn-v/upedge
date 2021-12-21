package com.upedge.cms.modules.notice.response;

import com.upedge.cms.modules.notice.request.AppNoticeAddRequest;
import com.upedge.common.base.BaseResponse;

/**
 * @author author
 */
public class AppNoticeAddResponse extends BaseResponse {
    public AppNoticeAddResponse(int code, String msg, Object data, AppNoticeAddRequest req) {
        super(code,msg,data,req);
    }
}
