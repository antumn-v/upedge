package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ManagerProductAddRequest;

/**
 * @author gx
 */
public class ManagerProductAddResponse extends BaseResponse {
    public ManagerProductAddResponse(int code, String msg, Object data, ManagerProductAddRequest req) {
        super(code,msg,data,req);
    }
}
