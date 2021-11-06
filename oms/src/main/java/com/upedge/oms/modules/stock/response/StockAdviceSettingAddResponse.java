package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.request.StockAdviceSettingAddRequest;

/**
 * @author author
 */
public class StockAdviceSettingAddResponse extends BaseResponse {
    public StockAdviceSettingAddResponse(int code, String msg, Object data, StockAdviceSettingAddRequest req) {
        super(code,msg,data,req);
    }
}
