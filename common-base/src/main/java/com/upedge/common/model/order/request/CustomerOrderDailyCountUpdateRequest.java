package com.upedge.common.model.order.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CustomerOrderDailyCountUpdateRequest {
    BigDecimal balance;
    BigDecimal rebate;
    BigDecimal credit;
    Long customerId;
    Long paymentId;
    Integer orderType;
    Date payTime;
}
