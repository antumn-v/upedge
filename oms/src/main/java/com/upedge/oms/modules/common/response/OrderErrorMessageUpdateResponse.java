package com.upedge.oms.modules.common.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class OrderErrorMessageUpdateResponse extends BaseResponse {
    public OrderErrorMessageUpdateResponse(int code, String msg) {
        super(code,msg);
    }
}
