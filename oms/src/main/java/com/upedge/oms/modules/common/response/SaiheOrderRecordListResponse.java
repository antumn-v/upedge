package com.upedge.oms.modules.common.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.common.request.SaiheOrderRecordListRequest;

/**
 * @author gx
 */
public class SaiheOrderRecordListResponse extends BaseResponse {
    public SaiheOrderRecordListResponse(int code, String msg, Object data, SaiheOrderRecordListRequest req) {
        super(code,msg,data,req);
    }
}
