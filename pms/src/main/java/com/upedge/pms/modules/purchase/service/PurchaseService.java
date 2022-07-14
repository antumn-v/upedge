package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;

public interface PurchaseService {

    BaseResponse purchaseAdvice();

    BaseResponse previewPurchaseOrder();

    BaseResponse createPurchaseOrder();
}
