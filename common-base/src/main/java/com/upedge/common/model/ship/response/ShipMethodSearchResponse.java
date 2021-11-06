package com.upedge.common.model.ship.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class ShipMethodSearchResponse extends BaseResponse {

    public ShipMethodSearchResponse() {
    }

    public ShipMethodSearchResponse(int code, String msg) {
        super(code, msg);
    }

    public ShipMethodSearchResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
