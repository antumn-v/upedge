package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreCustomVariantRecordListRequest;
/**
 * @author gx
 */
public class StoreCustomVariantRecordListResponse extends BaseResponse {
    public StoreCustomVariantRecordListResponse(int code, String msg, Object data,StoreCustomVariantRecordListRequest req) {
        super(code,msg,data,req);
    }
}
