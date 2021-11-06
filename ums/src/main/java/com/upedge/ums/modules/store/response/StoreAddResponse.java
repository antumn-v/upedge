package com.upedge.ums.modules.store.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.store.request.StoreAddRequest;

/**
 * @author author
 */
public class StoreAddResponse extends BaseResponse {
    public StoreAddResponse(int code, String msg, Object data, StoreAddRequest req) {
        super(code,msg,data,req);
    }
}
