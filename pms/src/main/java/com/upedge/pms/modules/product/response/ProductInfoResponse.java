package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class ProductInfoResponse extends BaseResponse {
    public ProductInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
}
