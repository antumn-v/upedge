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

    private Integer seq;

    List<OrderItemPickInfoVo> orderItemPickInfoVos;
}
