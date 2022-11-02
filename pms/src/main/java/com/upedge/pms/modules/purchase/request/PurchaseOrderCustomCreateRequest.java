package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class PurchaseOrderCustomCreateRequest {

    @Size(min = 1)
    private List<PurchasePlan> purchasePlans;

}
