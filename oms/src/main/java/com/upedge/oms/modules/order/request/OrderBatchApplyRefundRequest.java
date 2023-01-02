package com.upedge.oms.modules.order.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class OrderBatchApplyRefundRequest {

    private boolean productFee;

    private boolean shipFee;

    @Size(min = 1)
    private List<Long> orderIds;
}
