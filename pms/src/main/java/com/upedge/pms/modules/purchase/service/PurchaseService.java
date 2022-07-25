package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;

import java.util.List;

public interface PurchaseService {

    BaseResponse purchaseAdvice(String warehouseCode);

    BaseResponse previewPurchaseOrder(PurchaseOrderCreateRequest request, Session session);

    List<Long> createPurchaseOrder(PurchaseOrderCreateRequest request, Session session) throws CustomerException;
}
