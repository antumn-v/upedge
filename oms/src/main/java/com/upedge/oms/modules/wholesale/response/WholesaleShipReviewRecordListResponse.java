package com.upedge.oms.modules.wholesale.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.wholesale.request.WholesaleShipReviewRecordListRequest;
/**
 * @author gx
 */
public class WholesaleShipReviewRecordListResponse extends BaseResponse {
    public WholesaleShipReviewRecordListResponse(int code, String msg, Object data,WholesaleShipReviewRecordListRequest req) {
        super(code,msg,data,req);
    }
}
