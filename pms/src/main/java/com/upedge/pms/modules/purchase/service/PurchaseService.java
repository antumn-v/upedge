package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.request.PurchaseAdviceRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;

import java.util.List;

public interface PurchaseService {

    BaseResponse customerPurchaseCount();

    BaseResponse cancelPurchase(List<Long> variantIds,Session session);

    BaseResponse restorePurchase(List<Long> variantIds,Session session);

    BaseResponse purchaseAdvice();

    BaseResponse purchaseAdvice(PurchaseAdviceRequest request);

    BaseResponse purchaseAdviceCache(PurchaseAdviceRequest request);

    BaseResponse previewPurchaseOrder(PurchaseOrderCreateRequest request, Session session);

    List<Long> createPurchaseOrder(PurchaseOrderCreateRequest request, Session session) throws CustomerException;

    BaseResponse createPurchaseOrder(PurchaseOrderCreateRequest request, Session session, Integer i);

    Long getNextPurchaseOrderId();
}
