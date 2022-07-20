package com.upedge.pms.modules.purchase.dao;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface PurchaseOrderItemDao{

    PurchaseOrderItem selectByPrimaryKey(PurchaseOrderItem record);

    int deleteByPrimaryKey(PurchaseOrderItem record);

    int updateByPrimaryKey(PurchaseOrderItem record);

    int updateByPrimaryKeySelective(PurchaseOrderItem record);

    int insert(PurchaseOrderItem record);

    int insertSelective(PurchaseOrderItem record);

    int insertByBatch(List<PurchaseOrderItem> list);

    List<PurchaseOrderItem> select(Page<PurchaseOrderItem> record);

    long count(Page<PurchaseOrderItem> record);

}
