package com.upedge.oms.modules.cart.request;

import com.upedge.oms.modules.cart.entity.Cart;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class CartUpdateRequest{


    /**
     * 数量
     */
    private Integer quantity;



    public Cart toCart(Long id){
        Cart cart=new Cart();
        cart.setId(id);

        cart.setQuantity(quantity);

        cart.setUpdateTime(new Date());

        return cart;
    }

}
