package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ImportPublishedRecordListRequest;
/**
 * @author gx
 */
public class ImportPublishedRecordListResponse extends BaseResponse {
    public ImportPublishedRecordListResponse(int code, String msg, Object data,ImportPublishedRecordListRequest req) {
        super(code,msg,data,req);
    }
}
