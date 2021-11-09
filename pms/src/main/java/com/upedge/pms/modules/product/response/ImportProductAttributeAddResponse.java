package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductAttributeAddRequest;
/**
 * @author gx
 */
public class ImportProductAttributeAddResponse extends BaseResponse {
    public ImportProductAttributeAddResponse(int code, String msg, Object data, ImportProductAttributeAddRequest req) {
        super(code,msg,data,req);
    }
}
