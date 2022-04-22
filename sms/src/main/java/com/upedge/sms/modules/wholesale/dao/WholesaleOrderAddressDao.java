package com.upedge.sms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;

import java.util.List;

/**
 * @author gx
 */
public interface WholesaleOrderAddressDao{

    WholesaleOrderAddress selectByOrderId(Long orderId);

    WholesaleOrderAddress selectByPrimaryKey(WholesaleOrderAddress record);

    int deleteByPrimaryKey(WholesaleOrderAddress record);

    int updateByPrimaryKey(WholesaleOrderAddress record);

    int updateByPrimaryKeySelective(WholesaleOrderAddress record);

    int insert(WholesaleOrderAddress record);

    int insertSelective(WholesaleOrderAddress record);

    int insertByBatch(List<WholesaleOrderAddress> list);

    List<WholesaleOrderAddress> select(Page<WholesaleOrderAddress> record);

    long count(Page<WholesaleOrderAddress> record);

}
