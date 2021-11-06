package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportPriceRuleAddRequest;

/**
 * @author gx
 */
public class ImportPriceRuleAddResponse extends BaseResponse {
    public ImportPriceRuleAddResponse(int code, String msg, Object data, ImportPriceRuleAddRequest req) {
        super(code,msg,data,req);
    }
}
