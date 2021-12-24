package com.upedge.pms.modules.alibaba.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.alibaba.request.AlibabaApiListRequest;
/**
 * @author gx
 */
public class AlibabaApiListResponse extends BaseResponse {
    public AlibabaApiListResponse(int code, String msg, Object data,AlibabaApiListRequest req) {
        super(code,msg,data,req);
    }
}
