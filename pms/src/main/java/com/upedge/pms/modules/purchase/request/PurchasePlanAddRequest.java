package com.upedge.pms.modules.purchase.request;

import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author gx
 */
@Data
public class PurchasePlanAddRequest{


    /**
    * 
    */
    private Long variantId;

    /**
    * 
    */
    private Integer quantity;

    @Size(min = 1)
    private List<PurchasePlan> purchasePlans;

}
