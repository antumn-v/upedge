package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;

public interface PurchaseService {

    BaseResponse purchaseAdvice(String warehouseCode);

    BaseResponse previewPurchaseOrder(PurchaseOrderCreateRequest request, Session session);

    BaseResponse createPurchaseOrder();
}
