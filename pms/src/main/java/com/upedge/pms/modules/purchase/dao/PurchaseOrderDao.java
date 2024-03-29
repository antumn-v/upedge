package com.upedge.pms.modules.purchase.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import com.upedge.pms.modules.purchase.request.PurchaseOrderListRequest;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gx
 */
public interface PurchaseOrderDao {

    List<PurchaseOrder> selectUnFinishOrder();

    List<PurchaseOrder> selectByRelateId(Long relateId);

    PurchaseOrder selectBy1688PurchaseId(String purchaseId);

    int updateOrderRevoke(@Param("id") Long id, @Param("date") Date date);

    int updateEditState(@Param("id") Long id, @Param("editState") Integer editState);

    int updateProductAmount(@Param("id") Long id, @Param("productAmount") BigDecimal productAmount);

    List<PurchaseOrder> selectPurchaseOrders(PurchaseOrderListRequest request);

    long countPurchaseOrders(PurchaseOrderListRequest request);

    PurchaseOrder selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    int insertByBatch(List<PurchaseOrder> list);

    List<PurchaseOrder> select(Page<PurchaseOrder> record);

    long count(Page<PurchaseOrder> record);

}
