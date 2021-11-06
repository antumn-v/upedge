package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class ApplyRechargeResponse extends BaseResponse {

    private static final long serialVersionUID = 3544912436549462767L;

    public ApplyRechargeResponse(int code, String msg) {
        super(code, msg);
    }

    public ApplyRechargeResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
