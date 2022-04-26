package com.upedge.sms.modules.photography.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPhotographyOrderPayRequest {

    private Long id;

    private BigDecimal payAmount;
}
