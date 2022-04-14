package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OverseaWarehouseServiceOrderUpdateFreightRequest {

    @NotNull
    Long orderId;

    @Size(min = 1)
    List<OverseaWarehouseServiceOrderFreight> orderFreights;

}
