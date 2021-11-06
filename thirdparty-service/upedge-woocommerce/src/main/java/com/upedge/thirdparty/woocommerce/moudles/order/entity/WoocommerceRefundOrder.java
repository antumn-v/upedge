package com.upedge.thirdparty.woocommerce.moudles.order.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class WoocommerceRefundOrder {


    /**
     * id : 724
     * date_created : 2017-03-21T16:55:37
     * date_created_gmt : 2017-03-21T19:55:37
     * amount : 9.00
     * reason :
     * refunded_by : 1
     * refunded_payment : false
     * meta_data : []
     * line_items : [{"id":314,"name":"Woo Album #2","product_id":87,"variation_id":0,"quantity":-1,"tax_class":"","subtotal":"-9.00","subtotal_tax":"0.00","total":"-9.00","total_tax":"0.00","taxes":[],"meta_data":[{"id":2076,"key":"_refunded_item_id","value":"311"}],"sku":"","price":-9}]
     * _links : {"self":[{"href":"https://example.com/wp-json/wc/v3/orders/723/refunds/724"}],"collection":[{"href":"https://example.com/wp-json/wc/v3/orders/723/refunds"}],"up":[{"href":"https://example.com/wp-json/wc/v3/orders/723"}]}
     */

    private String id;
    private Date date_created;
    private Date date_created_gmt;
    private BigDecimal amount;
    private String reason;

    private List<WoocommerceOrderItem> line_items;

}
