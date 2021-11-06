package com.upedge.common.model.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DailySales {

    String days;

    BigDecimal sales;
}
