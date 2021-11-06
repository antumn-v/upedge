package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.stock.entity.StockAdviceSetting;

/**
 * @author author
 */
public class StockAdviceSettingInfoResponse extends BaseResponse {
    public StockAdviceSettingInfoResponse(int code, String msg, Object data, Object req) {
        super(code,msg,data,req);
    }

    public StockAdviceSettingInfoResponse(int successCode, String messageSuccess, StockAdviceSetting result) {
        super(successCode,messageSuccess,result);
    }
}
