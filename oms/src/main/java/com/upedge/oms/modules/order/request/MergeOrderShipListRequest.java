package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MergeOrderShipListRequest {

    @Size(min = 1)
    List<Long> orderIds;
}
