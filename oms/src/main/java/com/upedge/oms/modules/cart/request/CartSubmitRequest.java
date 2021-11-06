package com.upedge.oms.modules.cart.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class CartSubmitRequest {

    @NotNull
    private Integer cartType;

    @NotNull
    private List<Integer> ids;
}
