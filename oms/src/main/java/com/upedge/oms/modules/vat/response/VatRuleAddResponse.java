package com.upedge.oms.modules.vat.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.vat.request.VatRuleAddRequest;

/**
 * @author author
 */
public class VatRuleAddResponse extends BaseResponse {
    public VatRuleAddResponse(int code, String msg, Object data, VatRuleAddRequest req) {
        super(code,msg,data,req);
    }
    public VatRuleAddResponse(int code, String msg) {
        super(code,msg);
    }
}
