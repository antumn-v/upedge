package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportPublishedRecordAddRequest;
/**
 * @author gx
 */
public class ImportPublishedRecordAddResponse extends BaseResponse {
    public ImportPublishedRecordAddResponse(int code, String msg, Object data, ImportPublishedRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
