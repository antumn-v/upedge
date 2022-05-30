package com.upedge.oms.modules.statistics.vo;

import com.upedge.common.model.account.vo.InvoiceProductVo;
import com.upedge.oms.modules.order.vo.AppOrderVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class InvoiceDetailVo extends InvoiceVo {
    /**
     * dischargeAmount
     */
    BigDecimal dischargeAmount;
    /**
     * fixFee
     */
    BigDecimal fixFee;

    private BigDecimal shipPrice;

    private BigDecimal productAmount;

    private BigDecimal payAmount;

    BigDecimal vatAmount;

    Date payTime;

    Integer paymentNumber;

    String storeOrderName;

    List<InvoiceProductVo> productVos;

    List<AppOrderVo> appOrderVos;
}
