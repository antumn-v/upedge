package com.upedge.pms.modules.product.dto;

import com.upedge.pms.modules.product.entity.Product;
import lombok.Data;

@Data
public class ProductListDto extends Product {

    private String sku;

    private String customerId;

    private Long storeId;

}
