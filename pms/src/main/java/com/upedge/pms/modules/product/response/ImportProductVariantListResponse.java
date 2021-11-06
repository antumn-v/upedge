package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductVariantListRequest;

/**
 * @author author
 */
public class ImportProductVariantListResponse extends BaseResponse {

    public ImportProductVariantListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public ImportProductVariantListResponse(int code, String msg, Object data, ImportProductVariantListRequest req) {
        super(code,msg,data,req);
    }
}
