package com.upedge.oms.modules.vat.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.vat.request.VatRuleLogListRequest;

/**
 * @author author
 */
public class VatRuleLogListResponse extends BaseResponse {
    public VatRuleLogListResponse(int code, String msg, Object data, VatRuleLogListRequest req) {
        super(code,msg,data,req);
    }
}
