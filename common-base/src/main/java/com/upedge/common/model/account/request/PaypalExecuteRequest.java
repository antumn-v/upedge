package com.upedge.common.model.account.request;

import com.upedge.common.model.account.PaypalOrder;
import lombok.Data;

@Data
public class PaypalExecuteRequest {

    String paymentId;
    String payerId;
    String token;

    PaypalOrder paypalOrder;

    public PaypalExecuteRequest() {
    }

    public PaypalExecuteRequest(String paymentId, String payerId, String token, PaypalOrder paypalOrder) {
        this.paymentId = paymentId;
        this.payerId = payerId;
        this.token = token;
        this.paypalOrder = paypalOrder;
    }
}
