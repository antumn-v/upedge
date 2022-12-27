package com.upedge.oms.modules.pick.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderPickInfoVo {

    private Long orderId;

    private Integer pickType;

    private Integer shipState;

    private Long waveNo;

    private Long packNo;

    private Boolean isPrintLabel;

    private Integer seq;

    private String trackingCode;

    private String logisticsOrderNo;

    List<OrderItemPickInfoVo> orderItemPickInfoVos;
}
