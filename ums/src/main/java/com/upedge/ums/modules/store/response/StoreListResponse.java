package com.upedge.ums.modules.store.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.store.request.StoreListRequest;

/**
 * @author author
 */
public class StoreListResponse extends BaseResponse {
    public StoreListResponse(int code, String msg, Object data, StoreListRequest req) {
        super(code,msg,data,req);
    }
}
