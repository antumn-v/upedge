package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.dto.PurchaseOrderItemReceiveDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PurchaseOrderReceiveRequest {

    @NotNull
    private Long orderId;

    private String trackingCode;

    @Size(min = 1)
    private List<PurchaseOrderItemReceiveDto> itemReceiveDtos;

}
