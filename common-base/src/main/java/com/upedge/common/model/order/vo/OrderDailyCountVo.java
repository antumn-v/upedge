package com.upedge.common.model.order.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDailyCountVo {

    Integer orderType; // 订单类型

    String dayDate; //日期

    BigDecimal orderAmount; //订单金额

    Long orderNum; //客户订单数

    BigDecimal shipPrice; // 运费

    BigDecimal productAmount; // 产品费

    BigDecimal productDischargeAmount; // 库存抵扣金额

    BigDecimal fixFee;  // paypal 手续费

    BigDecimal vatAmount; // vat 费用

    BigDecimal payAmount; // 支付金额：product_amount + ship_price + vat + fix_fee - discharge

    BigDecimal creditAmount; // 入账金额  入账金额：product_amount + ship_price + vat
}
