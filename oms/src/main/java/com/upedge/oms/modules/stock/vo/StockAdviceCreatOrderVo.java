package com.upedge.oms.modules.stock.vo;

import com.upedge.oms.modules.cart.entity.Cart;
import lombok.Data;

import java.util.List;

@Data
public class StockAdviceCreatOrderVo {

    private List<Cart> carts ;

    private Long orderId;
}
