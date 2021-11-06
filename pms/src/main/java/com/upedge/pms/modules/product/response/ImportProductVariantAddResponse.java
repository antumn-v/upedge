package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductVariantAddRequest;

/**
 * @author author
 */
public class ImportProductVariantAddResponse extends BaseResponse {
    public ImportProductVariantAddResponse(int code, String msg, Object data, ImportProductVariantAddRequest req) {
        super(code,msg,data,req);
    }
}
