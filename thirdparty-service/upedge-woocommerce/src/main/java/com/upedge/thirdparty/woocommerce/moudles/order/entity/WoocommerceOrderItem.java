package com.upedge.thirdparty.woocommerce.moudles.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class WoocommerceOrderItem {

    private String id;
    private String name;
    private String product_id;
    private String variation_id;
    private int quantity;
    private BigDecimal tax_class;
    private BigDecimal subtotal;
    private BigDecimal subtotal_tax;
    private BigDecimal total;
    private BigDecimal total_tax;
    private String sku;
    private BigDecimal price;
    private String parent_name;

}
