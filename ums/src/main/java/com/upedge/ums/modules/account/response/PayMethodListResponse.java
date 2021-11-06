package com.upedge.ums.modules.account.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.entity.PayMethod;

import java.util.List;

/**
 *
 * @author jiaqi
 * @date 2020/10/28
 */
public class PayMethodListResponse extends BaseResponse{
    private static final long serialVersionUID = 4730765771691014245L;

    public PayMethodListResponse(int code, String message, List<PayMethod> list) {
      super(code,message,list);
    }

    public PayMethodListResponse(int code, String msg, Object data, Object req) {
        super(code, msg, data, req);
    }
}
