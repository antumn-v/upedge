package com.upedge.thirdparty.shopify.moudles.inventory.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class InventoryItem {

    private Long id;
    private String sku;
    private String created_at;
    private String updated_at;
    private boolean requires_shipping;
    private BigDecimal cost;
    private Object country_code_of_origin;
    private Object province_code_of_origin;
    private Object harmonized_system_code;
    private boolean tracked;
    private String admin_graphql_api_id;
    private List<?> country_harmonized_system_codes;


}
