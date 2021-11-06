package com.upedge.oms.modules.wholesale.service;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleReshipInfo;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleReshipInfoService{

    WholesaleReshipInfo selectByPrimaryKey(Long orderId);

    int deleteByPrimaryKey(Long orderId);

    int updateByPrimaryKey(WholesaleReshipInfo record);

    int updateByPrimaryKeySelective(WholesaleReshipInfo record);

    int insert(WholesaleReshipInfo record);

    int insertSelective(WholesaleReshipInfo record);

    List<WholesaleReshipInfo> select(Page<WholesaleReshipInfo> record);

    long count(Page<WholesaleReshipInfo> record);
}

