package com.upedge.oms.modules.cart.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.cart.entity.Cart;
import lombok.Data;

/**
 * @author author
 */
@Data
public class CartListRequest extends Page<Cart> {

    private String orderBy = "id desc";

}
