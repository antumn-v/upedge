package com.upedge.oms.modules.order.request;

import com.upedge.common.model.oms.order.OrderExcelItemDto;
import com.upedge.oms.modules.order.entity.OrderAddress;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OrderCustomCreateRequest {
    @NotNull
    private Long storeId;

    @NotBlank
    private String orderNum;

    @NotNull
    private OrderAddress address;
    @Size(min = 1)
    List<OrderExcelItemDto> itemDtos;
}
