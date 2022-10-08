package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.request.PurchasePlanListRequest;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gx
 */
public interface PurchasePlanDao {

    int addQuantityById(@Param("id") Integer id, @Param("quantity") Integer quantity);

    int updatePriceById(@Param("id") Integer id, @Param("price") BigDecimal price);

    int updateVariantStockByIds(@Param("ids") List<Integer> ids);

    int updatePurchaseOrderIdByIds(@Param("ids") List<Integer> ids, @Param("purchaseOrderId") Long purchaseOrderId);

    PurchasePlan selectBySkuAndState(@Param("sku") String sku, @Param("state") Integer state);

    List<PurchasePlan> selectByIds(@Param("ids") List<Integer> ids);

    List<Long> selectPlaningVariantIds();

    PurchasePlan selectByPrimaryKey(PurchasePlan record);

    int deleteByPrimaryKey(PurchasePlan record);

    int updateByPrimaryKey(PurchasePlan record);

    int updateByPrimaryKeySelective(PurchasePlan record);

    int insert(PurchasePlan record);

    int insertSelective(PurchasePlan record);

    int insertByBatch(List<PurchasePlan> list);

    List<PurchasePlan> select(PurchasePlanListRequest request);

    long count(Page<PurchasePlan> record);

}
