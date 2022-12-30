package com.upedge.oms.modules.stock.response;

import com.upedge.common.base.BaseResponse;
import lombok.Data;

@Data
public class ApplyStockOrderRefundResponse extends BaseResponse {

    private Long stockOrderRefundId;

    public ApplyStockOrderRefundResponse(int code, String msg){
        super(code,msg);
    }

    public ApplyStockOrderRefundResponse(int code, String msg, Long stockOrderRefundId) {
        super(code, msg);
        this.stockOrderRefundId = stockOrderRefundId;
    }
}
