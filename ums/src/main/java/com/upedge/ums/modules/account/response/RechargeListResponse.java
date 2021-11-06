package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class RechargeListResponse extends BaseResponse {

    private static final long serialVersionUID = -765648948805453373L;

    public RechargeListResponse(int code, String msg, Object data, Object req) {
        super(code, msg, data, req);
    }
}
