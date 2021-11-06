package com.upedge.ums.modules.store.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.store.request.StoreAttrAddRequest;

/**
 * @author author
 */
public class StoreAttrAddResponse extends BaseResponse {
    public StoreAttrAddResponse(int code, String msg, Object data, StoreAttrAddRequest req) {
        super(code,msg,data,req);
    }
}
