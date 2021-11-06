package com.upedge.common.model.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 海桐
 */
@Data
public class TransactionDetail {

    private Long orderId;

    private Integer orderType;

    private BigDecimal amount;

    private Long paymentId;

    private Date payTime;


}
