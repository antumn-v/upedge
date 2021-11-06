package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderAddress;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleOrderAddressDao{

    WholesaleOrderAddress selectByPrimaryKey(WholesaleOrderAddress record);

    int updateByOrderId(WholesaleOrderAddress address);

    int deleteByPrimaryKey(WholesaleOrderAddress record);

    int updateByPrimaryKey(WholesaleOrderAddress record);

    int updateByPrimaryKeySelective(WholesaleOrderAddress record);

    int insert(WholesaleOrderAddress record);

    int insertSelective(WholesaleOrderAddress record);

    int insertByBatch(List<WholesaleOrderAddress> list);

    List<WholesaleOrderAddress> select(Page<WholesaleOrderAddress> record);

    long count(Page<WholesaleOrderAddress> record);

    WholesaleOrderAddress queryWholesaleOrderAddressByOrderId(Long orderId);
}
