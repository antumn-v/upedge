package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportProductImageAddRequest;

/**
 * @author author
 */
public class ImportProductImageAddResponse extends BaseResponse {
    public ImportProductImageAddResponse(int code, String msg, Object data, ImportProductImageAddRequest req) {
        super(code,msg,data,req);
    }
}
