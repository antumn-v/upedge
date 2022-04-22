package com.upedge.sms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;

import java.util.List;

/**
 * @author gx
 */
public interface WholesaleOrderDao{


    int updateOrderAsPaid(WholesaleOrder wholesaleOrder);

    WholesaleOrder selectByPrimaryKey(WholesaleOrder record);

    int deleteByPrimaryKey(WholesaleOrder record);

    int updateByPrimaryKey(WholesaleOrder record);

    int updateByPrimaryKeySelective(WholesaleOrder record);

    int insert(WholesaleOrder record);

    int insertSelective(WholesaleOrder record);

    int insertByBatch(List<WholesaleOrder> list);

    List<WholesaleOrder> select(Page<WholesaleOrder> record);

    long count(Page<WholesaleOrder> record);

}
