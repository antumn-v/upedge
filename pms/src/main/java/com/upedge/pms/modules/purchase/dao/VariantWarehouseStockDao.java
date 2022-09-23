package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.request.VariantSafeStockUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantStockListRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockDeleteRequest;
import com.upedge.pms.modules.purchase.vo.VariantWarehouseStockVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockDao {

    int restoreStockByLockStock(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("changeQuantity") Integer changeQuantity);

    int markAsDeleted(VariantWarehouseStockDeleteRequest request);

    List<VariantWarehouseStockVo> selectVariantStocks(VariantStockListRequest variantStockListRequest);

    long countVariantStocks(VariantStockListRequest variantStockListRequest);

    int reduceVariantLockStock(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("changeQuantity") Integer changeQuantity);

    int updateVariantPurchaseStock(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("changeQuantity") Integer changeQuantity);

    VariantWarehouseStock selectByPrimaryKey(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode);

    List<VariantWarehouseStock> selectByVariantIdsAndWarehouseCode(@Param("variantIds") List<Long> variantIds, @Param("warehouseCode") String warehouseCode);

    int updateVariantSafeStock(VariantSafeStockUpdateRequest request);

    int updateVariantWarehouseAvailableStock(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("changeQuantity") Integer changeQuantity);

    int updateVariantStockEx(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("quantity") Integer quantity);

    int updateVariantStockIm(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("quantity") Integer quantity, @Param("processType") Integer processType);

    int deleteByPrimaryKey(VariantWarehouseStock record);

    int updateByPrimaryKey(VariantWarehouseStock record);

    int updateByPrimaryKeySelective(VariantWarehouseStock record);

    int insert(VariantWarehouseStock record);

    int insertSelective(VariantWarehouseStock record);

    int insertByBatch(List<VariantWarehouseStock> list);

    List<VariantWarehouseStock> select(Page<VariantWarehouseStock> record);

    long count(Page<VariantWarehouseStock> record);

}
