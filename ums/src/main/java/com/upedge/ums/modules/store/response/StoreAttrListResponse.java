package com.upedge.ums.modules.store.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.store.request.StoreAttrListRequest;
/**
 * @author gx
 */
public class StoreAttrListResponse extends BaseResponse {
    public StoreAttrListResponse(int code, String msg, Object data,StoreAttrListRequest req) {
        super(code,msg,data,req);
    }
}
