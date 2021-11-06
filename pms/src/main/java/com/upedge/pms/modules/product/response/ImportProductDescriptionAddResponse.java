package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductDescriptionAddRequest;

/**
 * @author author
 */
public class ImportProductDescriptionAddResponse extends BaseResponse {
    public ImportProductDescriptionAddResponse(int code, String msg, Object data, ImportProductDescriptionAddRequest req) {
        super(code,msg,data,req);
    }
}
