package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.request.VariantStockExImRecordUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantStockUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockListRequest;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockService{

    List<VariantWarehouseStock> selectByVariantIdsAndWarehouseCode(List<Long> variantIds, String warehouseCode);

    boolean updateVariantPurchaseStockByPlan(List<PurchasePlan> purchasePlans);

    boolean orderCheckStock(OrderItemQuantityVo orderItemQuantityVo) throws Exception;

    BaseResponse variantWarehouseStockList(VariantWarehouseStockListRequest request);

    BaseResponse updateVariantStock(VariantStockUpdateRequest request, Session session);

    BaseResponse variantStockEx(VariantStockExImRecordUpdateRequest request, Session session);

    BaseResponse variantStockIm(VariantStockExImRecordUpdateRequest request, Session session);

    VariantWarehouseStock selectByPrimaryKey(Long variantId,String warehouseCode);

    int deleteByPrimaryKey(Long variantId);

    int updateByPrimaryKey(VariantWarehouseStock record);

    int updateByPrimaryKeySelective(VariantWarehouseStock record);

    int insert(VariantWarehouseStock record);

    int insertSelective(VariantWarehouseStock record);

    List<VariantWarehouseStock> select(Page<VariantWarehouseStock> record);

    long count(Page<VariantWarehouseStock> record);
}

