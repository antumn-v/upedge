package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.StoreCustomProductAddRequest;

/**
 * @author xwCui
 */
public class StoreCustomProductAddResponse extends BaseResponse {
    public StoreCustomProductAddResponse(int code, String msg, Object data, StoreCustomProductAddRequest req) {
        super(code,msg,data,req);
    }
}
