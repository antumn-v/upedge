package com.upedge.oms.modules.vat.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.vat.request.VatRuleItemListRequest;

/**
 * @author author
 */
public class VatRuleItemListResponse extends BaseResponse {
    public VatRuleItemListResponse(int code, String msg, Object data, VatRuleItemListRequest req) {
        super(code,msg,data,req);
    }
}
