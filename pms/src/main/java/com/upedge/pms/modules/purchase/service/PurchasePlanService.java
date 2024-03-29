package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.request.PurchasePlanAddRequest;
import com.upedge.pms.modules.purchase.request.PurchasePlanListRequest;
import com.upedge.pms.modules.purchase.request.PurchasePlanUpdateRequest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gx
 */
public interface PurchasePlanService{

    BaseResponse selectPurchasePlanList();

    BaseResponse updatePurchaseSku(PurchasePlanUpdateRequest request,Session session);

    int updatePriceById(Integer id,BigDecimal price);

    int updateVariantStockByIds(List<Integer> ids);

    int updatePurchaseOrderIdByIds(List<Integer> Ids,Long purchaseOrderId);

    List<PurchasePlan> selectByIds( List<Integer> ids);

    List<Long> selectPlaningVariantIds();

    BaseResponse addPurchasePlan(PurchasePlanAddRequest request, Session session);

    PurchasePlan selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(PurchasePlan record);

    int updateByPrimaryKeySelective(PurchasePlan record);

    int insert(PurchasePlan record);

    int insertSelective(PurchasePlan record);

    List<PurchasePlan> select(PurchasePlanListRequest request);

    long count(Page<PurchasePlan> record);
}

