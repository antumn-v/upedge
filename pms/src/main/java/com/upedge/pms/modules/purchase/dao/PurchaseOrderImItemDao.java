package com.upedge.pms.modules.purchase.dao;

import com.upedge.pms.modules.purchase.entity.PurchaseOrderImItem;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface PurchaseOrderImItemDao{

    PurchaseOrderImItem selectByPrimaryKey(PurchaseOrderImItem record);

    int deleteByPrimaryKey(PurchaseOrderImItem record);

    int updateByPrimaryKey(PurchaseOrderImItem record);

    int updateByPrimaryKeySelective(PurchaseOrderImItem record);

    int insert(PurchaseOrderImItem record);

    int insertSelective(PurchaseOrderImItem record);

    int insertByBatch(List<PurchaseOrderImItem> list);

    List<PurchaseOrderImItem> select(Page<PurchaseOrderImItem> record);

    long count(Page<PurchaseOrderImItem> record);

}
