package com.upedge.oms.modules.vat.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.vat.request.CustomerVatRuleListRequest;
/**
 * @author gx
 */
public class CustomerVatRuleListResponse extends BaseResponse {
    public CustomerVatRuleListResponse(int code, String msg, Object data,CustomerVatRuleListRequest req) {
        super(code,msg,data,req);
    }
}
