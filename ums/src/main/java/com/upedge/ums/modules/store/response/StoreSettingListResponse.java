package com.upedge.ums.modules.store.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.store.request.StoreSettingListRequest;
/**
 * @author gx
 */
public class StoreSettingListResponse extends BaseResponse {
    public StoreSettingListResponse(int code, String msg, Object data,StoreSettingListRequest req) {
        super(code,msg,data,req);
    }
}
