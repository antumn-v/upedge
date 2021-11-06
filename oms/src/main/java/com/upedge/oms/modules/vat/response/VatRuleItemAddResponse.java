package com.upedge.oms.modules.vat.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.vat.request.VatRuleItemAddRequest;

/**
 * @author author
 */
public class VatRuleItemAddResponse extends BaseResponse {
    public VatRuleItemAddResponse(int code, String msg, Object data, VatRuleItemAddRequest req) {
        super(code,msg,data,req);
    }
}
