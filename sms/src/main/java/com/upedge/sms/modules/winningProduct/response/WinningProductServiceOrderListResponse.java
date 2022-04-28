package com.upedge.sms.modules.winningProduct.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.winningProduct.request.WinningProductServiceOrderListRequest;
/**
 * @author gx
 */
public class WinningProductServiceOrderListResponse extends BaseResponse {
    public WinningProductServiceOrderListResponse(int code, String msg, Object data,WinningProductServiceOrderListRequest req) {
        super(code,msg,data,req);
    }
}
