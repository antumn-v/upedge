package com.upedge.oms.modules.cart.request;

import com.upedge.oms.modules.cart.entity.Cart;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 海桐
 */
@Data
public class CartSubmitOrderRequest  extends CartSubmitListRequest{

    @NotNull
    private Long warehouseId;

    private Integer payMethod = null;

    @NotNull
    private Integer cartType;

    @NotNull
    private List<Cart> carts;
}
