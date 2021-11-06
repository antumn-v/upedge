package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductImageListRequest;

/**
 * @author author
 */
public class ImportProductImageListResponse extends BaseResponse {

    public ImportProductImageListResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public ImportProductImageListResponse(int code, String msg, Object data, ImportProductImageListRequest req) {
        super(code,msg,data,req);
    }
}
