package com.upedge.sms.modules.wholesale.service;

import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface WholesaleOrderItemService{

    List<WholesaleOrderItem> selectByOrderId(Long orderId);

    WholesaleOrderItem selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleOrderItem record);

    int updateByPrimaryKeySelective(WholesaleOrderItem record);

    int insert(WholesaleOrderItem record);

    int insertByBatch(List<WholesaleOrderItem> wholesaleOrderItems);

    int insertSelective(WholesaleOrderItem record);

    List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record);

    long count(Page<WholesaleOrderItem> record);
}

