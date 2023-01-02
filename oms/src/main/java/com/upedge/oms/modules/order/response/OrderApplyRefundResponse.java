package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import lombok.Data;

@Data
public class OrderApplyRefundResponse extends BaseResponse {

    private Long refundId;

    public OrderApplyRefundResponse(int code, String msg, Long refundId) {
        super(code, msg);
        this.refundId = refundId;
    }

    public OrderApplyRefundResponse(int code, String msg) {
        super(code, msg);
    }

    public OrderApplyRefundResponse(int code, String msg, Object data, Long refundId) {
        super(code, msg, data);
        this.refundId = refundId;
    }
}
