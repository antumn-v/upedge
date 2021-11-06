package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class MarketPlaceListResponse extends BaseResponse {


    public MarketPlaceListResponse(int code, String msg) {
        super(code, msg);
    }


    public MarketPlaceListResponse(int code, String msg, Object data, Object req) {
        super(code, msg, data, req);
    }
}
