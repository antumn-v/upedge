package com.upedge.common.model.order.vo;

import lombok.Data;

@Data
public class UplodaSaiheOnMqVo {
    private Long paymentId;
    private Integer orderType;

    public UplodaSaiheOnMqVo(Long paymentId, Integer orderType) {
        this.paymentId = paymentId;
        this.orderType = orderType;
    }

    public UplodaSaiheOnMqVo() {
    }
}
