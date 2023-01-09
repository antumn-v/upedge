package com.upedge.oms.modules.common.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class OrderErrorMessageDelResponse extends BaseResponse {
    public OrderErrorMessageDelResponse(int code, String msg) {
        super(code,msg);
    }
}
