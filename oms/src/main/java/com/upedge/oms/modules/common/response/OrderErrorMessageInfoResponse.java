package com.upedge.oms.modules.common.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author gx
 */
public class OrderErrorMessageInfoResponse extends BaseResponse {
    public OrderErrorMessageInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }
}
