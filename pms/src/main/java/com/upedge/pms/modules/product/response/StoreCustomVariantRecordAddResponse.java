package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreCustomVariantRecordAddRequest;
/**
 * @author gx
 */
public class StoreCustomVariantRecordAddResponse extends BaseResponse {
    public StoreCustomVariantRecordAddResponse(int code, String msg, Object data, StoreCustomVariantRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
