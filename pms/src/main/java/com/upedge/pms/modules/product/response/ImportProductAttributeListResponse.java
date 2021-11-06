package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductAttributeListRequest;

/**
 * @author author
 */
public class ImportProductAttributeListResponse extends BaseResponse {
    public ImportProductAttributeListResponse(int code, String msg, Object data, ImportProductAttributeListRequest req) {
        super(code,msg,data,req);
    }
}
