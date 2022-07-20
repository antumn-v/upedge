package com.upedge.pms.modules.purchase.dto;

import lombok.Data;

@Data
public class PurchaseOrderCreateDto {

    String purchaseSku;

    Double quantity;
}
