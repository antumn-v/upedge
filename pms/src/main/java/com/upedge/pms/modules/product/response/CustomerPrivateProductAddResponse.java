package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.request.CustomerPrivateProductAddRequest;
/**
 * @author gx
 */
public class CustomerPrivateProductAddResponse extends BaseResponse {
    public CustomerPrivateProductAddResponse(int code, String msg, Object data, CustomerPrivateProductAddRequest req) {
        super(code,msg,data,req);
    }
}
