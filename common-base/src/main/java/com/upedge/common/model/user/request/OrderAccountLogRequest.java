package com.upedge.common.model.user.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 根据订单id + 订单类型查询该订单支付信息
 */
@Data
public class OrderAccountLogRequest {

    @NotNull(message = " 订单 类型 为  null")
    private Integer orderType;

    @NotNull(message = "订单id 为   null")
    private List<Long> transactionIds;


}
