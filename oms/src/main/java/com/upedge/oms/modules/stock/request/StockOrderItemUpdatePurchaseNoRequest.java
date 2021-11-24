package com.upedge.oms.modules.stock.request;


import com.upedge.oms.modules.stock.dto.StockOrderItemPurchaseNoDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class StockOrderItemUpdatePurchaseNoRequest {

    @NotNull
    private Long orderId;

    @NotNull
    private List<StockOrderItemPurchaseNoDto> itemPurchaseNos;
}
