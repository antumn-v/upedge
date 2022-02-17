package com.upedge.oms.modules.stock.request;


import com.upedge.oms.modules.stock.vo.CustomerSkuStockVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ManualAddCustomerStockRequest {

    @NotNull
    Long productId;

    @NotNull
    Long customerId;

    Integer warehouseCode;

    @Size(min = 1)
    List<CustomerSkuStockVo> customerSkuStockVos;

}
