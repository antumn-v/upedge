package com.upedge.common.model.account.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceVo {

    Integer orderType;

    Long paymentId;

    Long customerId;
    /**
     * freight
     */
    BigDecimal shipPrice;


    /**
     * productAmount
     */
    BigDecimal productAmount;

    /**
     * payAmount
     */
    BigDecimal payAmount;

    Date payTime;

    List<InvoiceProductVo> productVos;


}
