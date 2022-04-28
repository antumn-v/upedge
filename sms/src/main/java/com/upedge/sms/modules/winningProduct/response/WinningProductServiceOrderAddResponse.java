package com.upedge.sms.modules.winningProduct.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.winningProduct.request.WinningProductServiceOrderAddRequest;
/**
 * @author gx
 */
public class WinningProductServiceOrderAddResponse extends BaseResponse {
    public WinningProductServiceOrderAddResponse(int code, String msg, Object data, WinningProductServiceOrderAddRequest req) {
        super(code,msg,data,req);
    }
}
