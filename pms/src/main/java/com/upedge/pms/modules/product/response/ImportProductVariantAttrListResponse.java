package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductVariantAttrListRequest;

/**
 * @author author
 */
public class ImportProductVariantAttrListResponse extends BaseResponse {
    public ImportProductVariantAttrListResponse(int code, String msg, Object data, ImportProductVariantAttrListRequest req) {
        super(code,msg,data,req);
    }
}
