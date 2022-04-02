package com.upedge.oms.modules.statistics.vo;

import com.upedge.oms.modules.order.vo.AppOrderVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceDetailVo {

    Long paymentId;

    Long customerId;


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

    Integer paymentNumber;

    String storeOrderName;

    List<AppOrderVo> appOrderVos;
}
