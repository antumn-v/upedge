package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefundItem;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleRefundItemDao{

    WholesaleRefundItem selectByPrimaryKey(WholesaleRefundItem record);

    int deleteByPrimaryKey(WholesaleRefundItem record);

    int updateByPrimaryKey(WholesaleRefundItem record);

    int updateByPrimaryKeySelective(WholesaleRefundItem record);

    int insert(WholesaleRefundItem record);

    int insertSelective(WholesaleRefundItem record);

    int insertByBatch(List<WholesaleRefundItem> list);

    List<WholesaleRefundItem> select(Page<WholesaleRefundItem> record);

    long count(Page<WholesaleRefundItem> record);

}
