package com.upedge.oms.modules.order.request;

import com.upedge.common.model.ship.vo.ShipDetail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MergeOrderRequest {

    @Size(min = 2)
    List<Long> orderIds;
    @NotNull
    ShipDetail shipDetail;
}
