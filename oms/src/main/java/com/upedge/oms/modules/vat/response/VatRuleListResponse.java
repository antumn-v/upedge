package com.upedge.oms.modules.vat.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.vat.request.VatRuleListRequest;

/**
 * @author author
 */
public class VatRuleListResponse extends BaseResponse {
    public VatRuleListResponse(int code, String msg, Object data, VatRuleListRequest req) {
        super(code,msg,data,req);
    }
}
