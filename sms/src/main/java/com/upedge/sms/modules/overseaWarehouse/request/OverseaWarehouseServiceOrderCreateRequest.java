package com.upedge.sms.modules.overseaWarehouse.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OverseaWarehouseServiceOrderCreateRequest {

    @Size(min = 1,message = "Select at least one product!")
    List<Long> cartIds;

    @NotNull(message = "Location can't be null")
    String warehouseCode;

    @Size(min = 1,message = "Select at least one logistic!")
    List<Integer> logistics;

    @NotNull(message = "Title can't be null")
    String serviceTitle;

    String remark;
}
