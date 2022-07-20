package com.upedge.pms.modules.purchase.dao;

import com.upedge.pms.modules.purchase.entity.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface PurchaseOrderDao{

    PurchaseOrder selectByPrimaryKey(PurchaseOrder record);

    int deleteByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKey(PurchaseOrder record);

    int updateByPrimaryKeySelective(PurchaseOrder record);

    int insert(PurchaseOrder record);

    int insertSelective(PurchaseOrder record);

    int insertByBatch(List<PurchaseOrder> list);

    List<PurchaseOrder> select(Page<PurchaseOrder> record);

    long count(Page<PurchaseOrder> record);

}
