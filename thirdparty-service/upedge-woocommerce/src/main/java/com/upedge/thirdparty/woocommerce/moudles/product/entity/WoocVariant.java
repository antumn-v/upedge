package com.upedge.thirdparty.woocommerce.moudles.product.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class WoocVariant {

    private String id;
    private String date_created;
    private String date_created_gmt;
    private String date_modified;
    private String date_modified_gmt;
    private String description;
    private String permalink;
    private String sku;
    private BigDecimal price;
    private BigDecimal regular_price;
    private BigDecimal sale_price;
    private String date_on_sale_from;
    private String date_on_sale_from_gmt;
    private String date_on_sale_to;
    private String date_on_sale_to_gmt;
    private Boolean on_sale = true;
    private String status;
    private Boolean purchasable;
    private Boolean virtual = false;
    private Boolean downloadable;
    private Integer download_limit;
    private Integer download_expiry;
    private String tax_status;
    private String tax_class;
    private Boolean manage_stock;
    private Long stock_quantity;
    private String stock_status;
    private String backorders;
    private Boolean backorders_allowed;
    private Boolean backordered;
    private BigDecimal weight;
    private String shipping_class;
    private Integer shipping_class_id;
    private WoocImage image;
    private Integer menu_order;


    private List<VariantAttr> attributes;

    @Data
    public static class VariantAttr{
        private Integer id;
        private String name;
        private String option;
    }



}
