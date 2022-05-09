package com.upedge.ums.modules.user.service;

import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerVipRecordService{

    CustomerVipRecord selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(CustomerVipRecord record);

    int updateByPrimaryKeySelective(CustomerVipRecord record);

    int insert(CustomerVipRecord record);

    int insertSelective(CustomerVipRecord record);

    List<CustomerVipRecord> select(Page<CustomerVipRecord> record);

    long count(Page<CustomerVipRecord> record);
}

