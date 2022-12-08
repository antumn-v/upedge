package com.upedge.common.model.pms.request;

import com.upedge.common.model.pms.dto.CreatePurchaseOrderDto;
import lombok.Data;

import java.util.List;

@Data
public class CreatePurchaseOrderRequest {

    private Long stockOrderId;

    private List<CreatePurchaseOrderDto> createPurchaseOrderDtos;

    private boolean isPreview;
}
