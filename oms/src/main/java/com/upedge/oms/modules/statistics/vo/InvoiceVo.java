package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceVo {

    private String paymentId;
    private Date payTime;
    private BigDecimal amount;
    private Integer type;
    private Integer status;

}
