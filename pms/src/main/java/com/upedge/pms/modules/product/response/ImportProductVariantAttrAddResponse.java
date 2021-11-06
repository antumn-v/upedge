package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductVariantAttrAddRequest;

/**
 * @author author
 */
public class ImportProductVariantAttrAddResponse extends BaseResponse {
    public ImportProductVariantAttrAddResponse(int code, String msg, Object data, ImportProductVariantAttrAddRequest req) {
        super(code,msg,data,req);
    }
}
