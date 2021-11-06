package com.upedge.oms.modules.wholesale.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class QuantityVo {

  @NotNull
  private Integer quantity;
}
