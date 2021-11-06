package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;

public class ImportFavoriteResponse extends BaseResponse {
    public ImportFavoriteResponse(int code, String msg) {
        super(code,msg);
    }

    public ImportFavoriteResponse(int code, String msg, Object data) {
        super(code,msg,data);
    }
}
