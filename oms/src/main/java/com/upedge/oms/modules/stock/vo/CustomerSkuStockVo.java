package com.upedge.oms.modules.stock.vo;


import lombok.Data;

@Data
public class CustomerSkuStockVo {

    String variantSku;

    Long variantId;

    Integer stock;
}
