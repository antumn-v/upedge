package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class ApplyRechargeListResponse extends BaseResponse {
    private static final long serialVersionUID = -4264401456346945947L;

    public ApplyRechargeListResponse(int code, String msg) {
        super(code, msg);
    }

    public ApplyRechargeListResponse(int code, String msg, Object data, Object req) {
        super(code, msg, data, req);
    }
}
