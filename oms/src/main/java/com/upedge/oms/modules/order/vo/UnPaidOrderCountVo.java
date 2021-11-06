package com.upedge.oms.modules.order.vo;

import lombok.Data;

@Data
public class UnPaidOrderCountVo {

    String date;

    Long count;

    String amount;
}
