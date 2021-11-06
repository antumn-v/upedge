package com.upedge.oms.modules.order.vo;

import java.math.BigDecimal;

/**
 * Created by cjq on 2019/6/27.
 */
public class AppOrderDataVo {

    String dayDate;//日期

    Long orderNum;//下单数

    BigDecimal orderAmount;//下单金额

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
}
