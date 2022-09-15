package com.upedge.pms.modules.purchase.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author gx
 */
@Data
public class PurchasePlanAddRequest{


    /**
    * 
    */
    @NotNull
    private Long variantId;

    /**
    * 
    */
    @Min(1)
    private Integer quantity;

}
