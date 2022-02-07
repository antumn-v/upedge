package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.Product;
import lombok.Data;

@Data
public class PrivateWinningProductsRequest extends Page<Product> {
    /**
     *  Best Seller = turnover desc  (默认)
     *
     *  New Arrival = create_time desc
     *
     *  Best Feedback = score desc
     *
     *  Price  = price_range desc/asc
     */
    private String orderBy = "turnover desc";

    private Long customerId;
}
