package com.upedge.common.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class PaymentDetail {

    private Long paymentId;

    private Integer payMethod;

    private BigDecimal payAmount;

    private Integer orderType;

    private Long userId;

    private Long customerId;

    private Date payTime;

    private List<TransactionDetail> orderTransactions;
}
