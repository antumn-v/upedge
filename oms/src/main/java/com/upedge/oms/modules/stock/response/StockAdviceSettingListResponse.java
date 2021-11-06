package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockAdviceSettingListRequest;

/**
 * @author author
 */
public class StockAdviceSettingListResponse extends BaseResponse {
    public StockAdviceSettingListResponse(int code, String msg, Object data, StockAdviceSettingListRequest req) {
        super(code,msg,data,req);
    }
}
