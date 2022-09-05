package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PurchaseOrderCreateRequest {


    @Size(min = 1)
    public List<Integer> ids;
}
