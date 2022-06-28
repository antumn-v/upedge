package com.upedge.oms.modules.vat.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.vat.request.CustomerVatRuleAddRequest;
/**
 * @author gx
 */
public class CustomerVatRuleAddResponse extends BaseResponse {
    public CustomerVatRuleAddResponse(int code, String msg, Object data, CustomerVatRuleAddRequest req) {
        super(code,msg,data,req);
    }
}
