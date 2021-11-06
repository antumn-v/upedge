package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.ManagerProductListRequest;

/**
 * @author gx
 */
public class ManagerProductListResponse extends BaseResponse {
    public ManagerProductListResponse(int code, String msg, Object data, ManagerProductListRequest req) {
        super(code,msg,data,req);
    }
}
