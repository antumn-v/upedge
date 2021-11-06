package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class AppVariantShipsResponse extends BaseResponse {
    public AppVariantShipsResponse(int code, String msg){
        super(code, msg);
    }

    public AppVariantShipsResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }

    public AppVariantShipsResponse(int code, String msg, Object data, Object req) {
        super(code, msg, data, req);
    }
}
