package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleShipReviewRecordAddRequest;
/**
 * @author gx
 */
public class WholesaleShipReviewRecordAddResponse extends BaseResponse {
    public WholesaleShipReviewRecordAddResponse(int code, String msg, Object data, WholesaleShipReviewRecordAddRequest req) {
        super(code,msg,data,req);
    }
}
