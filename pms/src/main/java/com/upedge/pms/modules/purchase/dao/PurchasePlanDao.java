package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface PurchasePlanDao {

    int updateVariantStockByIds(@Param("ids") List<Integer> ids);

    int updatePurchaseOrderIdByIds(@Param("Ids") List<Integer> Ids, @Param("purchaseOrderId") Long purchaseOrderId);

    List<PurchasePlan> selectByIds(@Param("ids") List<Integer> ids);

    List<Long> selectPlaningVariantIds();

    PurchasePlan selectByPrimaryKey(PurchasePlan record);

    int deleteByPrimaryKey(PurchasePlan record);

    int updateByPrimaryKey(PurchasePlan record);

    int updateByPrimaryKeySelective(PurchasePlan record);

    int insert(PurchasePlan record);

    int insertSelective(PurchasePlan record);

    int insertByBatch(List<PurchasePlan> list);

    List<PurchasePlan> select(Page<PurchasePlan> record);

    long count(Page<PurchasePlan> record);

}
