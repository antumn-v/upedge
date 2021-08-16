package com.upedge.ums.modules.store.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.store.request.StoreSettingAddRequest;
/**
 * @author gx
 */
public class StoreSettingAddResponse extends BaseResponse {
    public StoreSettingAddResponse(int code, String msg, Object data, StoreSettingAddRequest req) {
        super(code,msg,data,req);
    }
}
