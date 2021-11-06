package com.upedge.oms.modules.common.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.common.request.SaiheOrderRecordAddRequest;

/**
 * @author gx
 */
public class SaiheOrderRecordAddResponse extends BaseResponse {
    public SaiheOrderRecordAddResponse(int code, String msg, Object data, SaiheOrderRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
