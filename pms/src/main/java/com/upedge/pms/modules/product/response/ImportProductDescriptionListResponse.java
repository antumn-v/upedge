package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductDescriptionListRequest;

/**
 * @author author
 */
public class ImportProductDescriptionListResponse extends BaseResponse {

    public ImportProductDescriptionListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public ImportProductDescriptionListResponse(int code, String msg, Object data, ImportProductDescriptionListRequest req) {
        super(code,msg,data,req);
    }
}
