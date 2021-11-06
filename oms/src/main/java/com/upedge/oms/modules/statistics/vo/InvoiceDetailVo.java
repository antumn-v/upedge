package com.upedge.oms.modules.statistics.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceDetailVo {

    Long paymentId;


    /**
     * dischargeAmount
     */
    BigDecimal dischargeAmount;


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


    /**
     * fixFee
     */
    BigDecimal fixFee;

    BigDecimal vatAmount;

    Date payTime;

    List<InvoiceProductVo> productVos;
}
