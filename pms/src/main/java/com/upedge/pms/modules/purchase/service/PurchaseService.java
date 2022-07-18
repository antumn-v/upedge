package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;

public interface PurchaseService {

    BaseResponse purchaseAdvice(String warehouseCode);

    BaseResponse previewPurchaseOrder(PurchaseOrderCreateRequest request);

    BaseResponse createPurchaseOrder();
}
