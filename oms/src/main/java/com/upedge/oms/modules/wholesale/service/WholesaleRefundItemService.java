package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleRefundItem;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleRefundItemService{

    WholesaleRefundItem selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(WholesaleRefundItem record);

    int updateByPrimaryKeySelective(WholesaleRefundItem record);

    int insert(WholesaleRefundItem record);

    int insertSelective(WholesaleRefundItem record);

    List<WholesaleRefundItem> select(Page<WholesaleRefundItem> record);

    long count(Page<WholesaleRefundItem> record);
}

