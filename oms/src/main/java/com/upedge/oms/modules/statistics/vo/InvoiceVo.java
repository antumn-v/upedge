package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceVo {

    private Long paymentId;
    private Long customerId;
    private Date payTime;
    private BigDecimal amount;
    private Integer type;
    private Integer status;

    private Integer orderCount;

    private Integer paymentNumber;

}
