package com.upedge.oms.modules.cart.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.cart.request.CartListRequest;

/**
 * @author author
 */
public class CartListResponse extends BaseResponse {

    public CartListResponse(int code, String msg) {
        super(code, msg);
    }

    public CartListResponse(int code, String msg, Object data, CartListRequest req) {
        super(code,msg,data,req);
    }
}
