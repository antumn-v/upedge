package com.upedge.thirdparty.woocommerce.moudles.product.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;
/**
 * @author 海桐
 */
@Data
public class WoocProduct {

    private String id;
    private String name;
    private String slug;
    private String permalink;
    private Date date_created;
    private Date date_created_gmt;
    private Date date_modified;
    private Date date_modified_gmt;
    private String type;
    private String status;
    private String featured;
    private String catalog_visibility;
    private String description;
    private String short_description;
    private String sku;
    private String price;
    private String regular_price;
    private String sale_price;
    private Date date_on_sale_from;
    private Date date_on_sale_from_gmt;
    private Date date_on_sale_to;
    private Date date_on_sale_to_gmt;
    private String price_html;
    private Boolean on_sale;
    private Boolean purchasable;
    private Integer total_sales;
    private Boolean virtual;
    private Boolean downloadable;
    private String download_limit;
    private String download_expiry;
    private String external_url;
    private String button_text;
    private String tax_status;
    private String tax_class;
    private String manage_stock;
    private String stock_quantity;
    private String stock_status;
    private String backorders;
    private Boolean backorders_allowed;
    private Boolean backordered;
    private Boolean sold_individually;
    private String weight;
    private Boolean shipping_required;
    private Boolean shipping_taxable;
    private String shipping_class;
    private String shipping_class_id;
    private Boolean reviews_allowed;
    private String average_rating;
    private String rating_count;
    private String parent_id;
    private String purchase_note;
    private String menu_order;

    private List<String> tags;
    private List<WoocImage> images;
    private List<WoocProductAttr> attributes;
    private List<String> variations;


}
