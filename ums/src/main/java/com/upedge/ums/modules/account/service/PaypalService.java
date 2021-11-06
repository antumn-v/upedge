package com.upedge.ums.modules.account.service;

import com.upedge.common.model.account.PaypalOrder;
import com.upedge.common.model.account.PaypalPayment;
import com.upedge.common.model.account.request.PaypalExecuteRequest;
import com.upedge.common.model.order.PaymentDetail;
import com.paypal.api.payments.Payment;

public interface PaypalService {

    String getPaypalOrderUrl(PaypalOrder paypalOrder);

    PaypalPayment executePayment(PaypalExecuteRequest request) ;

    boolean paypalPayOrders(PaymentDetail paymentDetail);

    Payment getPaymentDetail(String paymentId);
}
