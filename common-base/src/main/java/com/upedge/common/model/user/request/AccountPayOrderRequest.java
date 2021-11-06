package com.upedge.common.model.user.request;

import com.upedge.common.model.order.TransactionDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class AccountPayOrderRequest {

    Long paymentId;

    Long userId;

    BigDecimal payAmount;

    Integer orderType;

    Integer payType;

    BigDecimal fee;

    List<TransactionDetail> transactionDetails;
}
