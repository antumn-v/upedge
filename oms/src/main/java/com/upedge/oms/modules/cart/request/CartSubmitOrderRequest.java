package com.upedge.oms.modules.cart.request;

import com.upedge.oms.modules.cart.entity.Cart;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class CartSubmitOrderRequest {

    @NotNull
    private String warehouseCode;

    private Integer payMethod = null;

    @NotNull
    private Integer cartType;

    @NotNull
    private List<Long> ids;

    private List<Cart> carts;

}
