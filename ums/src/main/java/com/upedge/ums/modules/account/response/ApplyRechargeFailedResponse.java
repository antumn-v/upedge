package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class ApplyRechargeFailedResponse extends BaseResponse {
    private static final long serialVersionUID = -4047773239743165145L;

    public ApplyRechargeFailedResponse(int code, String msg) {
        super(code, msg);
    }
}
