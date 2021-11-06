package com.upedge.ums.modules.account.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.ums.modules.account.request.PayoneerPaymentRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentRequest;
import com.upedge.ums.modules.account.request.PaypalPaymentUpdateRemarkRequest;

public interface AdminPaymentService {
    BaseResponse payoneerPayments(PayoneerPaymentRequest request);

    BaseResponse paypalPayments(PaypalPaymentRequest request);

    BaseResponse paypalUpdateRemark(PaypalPaymentUpdateRemarkRequest request);
}
