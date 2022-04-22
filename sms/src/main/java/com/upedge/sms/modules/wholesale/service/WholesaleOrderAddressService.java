package com.upedge.sms.modules.wholesale.service;

import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface WholesaleOrderAddressService{

    WholesaleOrderAddress selectByOrderId(Long orderId);

    WholesaleOrderAddress selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WholesaleOrderAddress record);

    int updateByPrimaryKeySelective(WholesaleOrderAddress record);

    int insert(WholesaleOrderAddress record);

    int insertSelective(WholesaleOrderAddress record);

    List<WholesaleOrderAddress> select(Page<WholesaleOrderAddress> record);

    long count(Page<WholesaleOrderAddress> record);
}

