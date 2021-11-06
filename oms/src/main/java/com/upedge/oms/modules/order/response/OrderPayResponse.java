package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.common.vo.OrderPayCheckResultVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderPayResponse extends BaseResponse {

    public OrderPayResponse(int code, String msg) {
        super(code, msg);
    }

    public OrderPayResponse(int code, PayResponse data) {
        super(code, data);
    }

    public OrderPayResponse(int code, String msg, PayResponse data) {
        super(code, msg, data);
    }

    public OrderPayResponse(int code, String msg, PayResponse data, Object req) {
        super(code, msg, data, req);
    }

    @Data
    public static class PayResponse {
        Long paymentId;

        BigDecimal paAmount;

        Integer payMethod;

        Date payTime;

        OrderPayCheckResultVo.TradingDataVo tradingDataVo;

        public PayResponse(Long paymentId, BigDecimal paAmount, Integer payMethod, Date payTime) {
            this.paymentId = paymentId;
            this.paAmount = paAmount;
            this.payMethod = payMethod;
            this.payTime = payTime;

        }

        public PayResponse() {
        }

        public PayResponse(Long paymentId, BigDecimal paAmount, Integer payMethod, Date payTime, OrderPayCheckResultVo.TradingDataVo tradingDataVo) {
            this.paymentId = paymentId;
            this.paAmount = paAmount;
            this.payMethod = payMethod;
            this.payTime = payTime;
            this.tradingDataVo = tradingDataVo;
        }
    }




}
