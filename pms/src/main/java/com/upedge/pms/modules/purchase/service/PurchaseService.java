package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;

public interface PurchaseService {

    BaseResponse purchaseAdvice(String warehouseCode);

    BaseResponse previewPurchaseOrder();

    BaseResponse createPurchaseOrder();
}
