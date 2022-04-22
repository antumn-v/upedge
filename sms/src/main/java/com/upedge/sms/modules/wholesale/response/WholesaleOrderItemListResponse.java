package com.upedge.sms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderItemListRequest;
/**
 * @author gx
 */
public class WholesaleOrderItemListResponse extends BaseResponse {
    public WholesaleOrderItemListResponse(int code, String msg, Object data,WholesaleOrderItemListRequest req) {
        super(code,msg,data,req);
    }
}
