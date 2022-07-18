package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.dto.PurchaseOrderCreateDto;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PurchaseOrderCreateRequest {

    @Size(min = 1)
    private List<PurchaseOrderCreateDto> purchaseOrderCreateDtos;
}
