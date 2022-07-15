package com.upedge.common.model.pms.vo;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseAdviceVo {

    private String supplierName;

    private String warehouseCode;

    private List<PurchaseAdviceItemVo> purchaseAdviceItemVos;
}
