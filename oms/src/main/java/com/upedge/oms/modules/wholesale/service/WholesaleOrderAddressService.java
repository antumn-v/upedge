package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderAddress;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleOrderAddressService{


    WholesaleOrderAddress selectOrderAddressById(Long id);

    WholesaleOrderAddress selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleOrderAddress record);

    int updateByPrimaryKeySelective(WholesaleOrderAddress record);

    int insert(WholesaleOrderAddress record);

    int insertSelective(WholesaleOrderAddress record);

    List<WholesaleOrderAddress> select(Page<WholesaleOrderAddress> record);

    long count(Page<WholesaleOrderAddress> record);
}

