package com.upedge.common.model.cart.request;

import com.upedge.common.model.product.VariantDetail;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
@Data
public class CartAddRequest extends VariantDetail {

    /**
     * 备库=0，批发=1
     */
    @NotNull
    private Integer cartType;

    private Integer quantity;

    Long customerId;

    public CartAddRequest() {
    }
}

