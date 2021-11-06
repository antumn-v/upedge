package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderItemListRequest;

/**
 * @author author
 */
public class WholesaleOrderItemListResponse extends BaseResponse {
    public WholesaleOrderItemListResponse(int code, String msg, Object data, WholesaleOrderItemListRequest req) {
        super(code,msg,data,req);
    }
}
