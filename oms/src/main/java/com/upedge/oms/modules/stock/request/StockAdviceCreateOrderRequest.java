package com.upedge.oms.modules.stock.request;

import com.upedge.common.model.product.VariantQuantity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class StockAdviceCreateOrderRequest {

    @Size(min = 1)
    List<VariantQuantity> variantQuantities;

    @NotNull
    String warehouseCode;


}
