package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportPriceRuleListRequest;

/**
 * @author gx
 */
public class ImportPriceRuleListResponse extends BaseResponse {

    public ImportPriceRuleListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public ImportPriceRuleListResponse(int code, String msg, Object data, ImportPriceRuleListRequest req) {
        super(code,msg,data,req);
    }
}
