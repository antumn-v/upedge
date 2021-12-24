package com.upedge.pms.modules.alibaba.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.alibaba.request.AlibabaApiAddRequest;
/**
 * @author gx
 */
public class AlibabaApiAddResponse extends BaseResponse {
    public AlibabaApiAddResponse(int code, String msg, Object data, AlibabaApiAddRequest req) {
        super(code,msg,data,req);
    }
}
