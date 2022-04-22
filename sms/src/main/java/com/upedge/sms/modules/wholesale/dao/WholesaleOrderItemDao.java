package com.upedge.sms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;

import java.util.List;

/**
 * @author gx
 */
public interface WholesaleOrderItemDao{

    List<WholesaleOrderItem> selectByOrderId(Long orderId);

    WholesaleOrderItem selectByPrimaryKey(WholesaleOrderItem record);

    int deleteByPrimaryKey(WholesaleOrderItem record);

    int updateByPrimaryKey(WholesaleOrderItem record);

    int updateByPrimaryKeySelective(WholesaleOrderItem record);

    int insert(WholesaleOrderItem record);

    int insertSelective(WholesaleOrderItem record);

    int insertByBatch(List<WholesaleOrderItem> list);

    List<WholesaleOrderItem> select(Page<WholesaleOrderItem> record);

    long count(Page<WholesaleOrderItem> record);

}
