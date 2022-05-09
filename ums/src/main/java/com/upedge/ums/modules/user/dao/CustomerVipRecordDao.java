package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.CustomerVipRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface CustomerVipRecordDao{

    CustomerVipRecord selectByPrimaryKey(CustomerVipRecord record);

    int deleteByPrimaryKey(CustomerVipRecord record);

    int updateByPrimaryKey(CustomerVipRecord record);

    int updateByPrimaryKeySelective(CustomerVipRecord record);

    int insert(CustomerVipRecord record);

    int insertSelective(CustomerVipRecord record);

    int insertByBatch(List<CustomerVipRecord> list);

    List<CustomerVipRecord> select(Page<CustomerVipRecord> record);

    long count(Page<CustomerVipRecord> record);

}
