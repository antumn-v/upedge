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
    @NotBlank(message = "Store can not be null!")
    private String storeName;

    @NotBlank(message = "Order number can not be null!")
    private String orderNum;

    @NotNull(message = "Address can not be null!")
    private OrderAddress address;
    @Size(min = 1,message = "Item error")
    List<OrderExcelItemDto> itemDtos;
}
