package com.upedge.oms.modules.cart.request;

import com.upedge.common.model.user.vo.AddressVo;
import com.upedge.oms.modules.cart.entity.Cart;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CartSubmitWholesaleRequest {

    AddressVo addressVo;

    @NotNull
    private Integer cartType;

    @NotNull
    private List<Cart> carts;
}
