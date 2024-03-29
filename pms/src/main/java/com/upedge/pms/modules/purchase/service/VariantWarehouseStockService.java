package com.upedge.pms.modules.purchase.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.request.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockService{

    int updatePurchaseStockReduce(Long variantId, String warehouseCode, Integer quantity);

    int updateVariantPurchaseStock(Long variantId, String warehouseCode, Integer changeQuantity);
    int orderCancelShip(OrderItemQuantityVo orderItemQuantityVo) throws Exception;

    BaseResponse deleteVariantStock(VariantWarehouseStockDeleteRequest request,Session session);

    int restoreStockByLockStock(Long variantId, String warehouseCode,Integer changeQuantity);

    BaseResponse variantStockList(VariantStockListRequest request);

    BaseResponse packageShipped(OrderItemQuantityVo orderItemQuantityVo) throws Exception;

    int updateVariantSafeStock(VariantSafeStockUpdateRequest request);

    List<VariantWarehouseStock> selectByVariantIdsAndWarehouseCode(List<Long> variantIds, String warehouseCode);

    boolean updateVariantPurchaseStockByPlan(List<PurchasePlan> purchasePlans);

    boolean updateVariantPurchaseStockByPlan(Long variantId, String warehouseCode, Integer quantity);

    boolean orderCheckStock(OrderItemQuantityVo orderItemQuantityVo) throws Exception;

    BaseResponse variantWarehouseStockList(VariantWarehouseStockListRequest request);

    boolean purchaseOrderItemRevoke(PurchaseOrderItem purchaseOrderItem,String warehouseCode);

    BaseResponse updateVariantStock(VariantStockUpdateRequest request, Session session);
    /**
     * 出库
     * @param request
     * @param session
     * @return
     */
    BaseResponse variantStockEx(VariantStockExImRecordUpdateRequest request, Session session);
    /**
     * 入库
     * @param request
     * @param session
     * @return
     */
    BaseResponse variantStockIm(VariantStockExImRecordUpdateRequest request, Session session);

    VariantWarehouseStock selectByPrimaryKey(Long variantId,String warehouseCode);

    int deleteByPrimaryKey(Long variantId);

    int updateByPrimaryKey(VariantWarehouseStock record);

    int updateByPrimaryKeySelective(VariantWarehouseStock record);

    int insert(VariantWarehouseStock record);

    int insertByBatch(List<VariantWarehouseStock> records);

    int insertSelective(VariantWarehouseStock record);

    List<VariantWarehouseStock> select(Page<VariantWarehouseStock> record);

    long count(Page<VariantWarehouseStock> record);
}

