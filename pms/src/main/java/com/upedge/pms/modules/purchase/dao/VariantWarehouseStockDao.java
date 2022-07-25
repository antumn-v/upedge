package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface VariantWarehouseStockDao {

    int updateVariantPurchaseStock(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("changeQuantity") Integer changeQuantity);

    VariantWarehouseStock selectByPrimaryKey(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode);

    List<VariantWarehouseStock> selectByVariantIdsAndWarehouseCode(@Param("variantIds") List<Long> variantIds, @Param("warehouseCode") String warehouseCode);

    int updateVariantWarehouseSafeStock(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("changeQuantity") Integer changeQuantity);

    int updateVariantStockEx(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("quantity") Integer quantity);

    int updateVariantStockIm(@Param("variantId") Long variantId, @Param("warehouseCode") String warehouseCode, @Param("quantity") Integer quantity);

    int deleteByPrimaryKey(VariantWarehouseStock record);

    int updateByPrimaryKey(VariantWarehouseStock record);

    int updateByPrimaryKeySelective(VariantWarehouseStock record);

    int insert(VariantWarehouseStock record);

    int insertSelective(VariantWarehouseStock record);

    int insertByBatch(List<VariantWarehouseStock> list);

    List<VariantWarehouseStock> select(Page<VariantWarehouseStock> record);

    long count(Page<VariantWarehouseStock> record);

}
