package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdatePendingOrderRequest {

    @NotNull
    @Size(min = 1)
    private List<Long> ids;

}
