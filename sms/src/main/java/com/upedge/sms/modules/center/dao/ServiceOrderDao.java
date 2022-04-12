package com.upedge.sms.modules.center.dao;

import com.upedge.sms.modules.center.entity.ServiceOrder;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface ServiceOrderDao{

    ServiceOrder selectByPrimaryKey(ServiceOrder record);

    int deleteByPrimaryKey(ServiceOrder record);

    int updateByPrimaryKey(ServiceOrder record);

    int updateByPrimaryKeySelective(ServiceOrder record);

    int insert(ServiceOrder record);

    int insertSelective(ServiceOrder record);

    int insertByBatch(List<ServiceOrder> list);

    List<ServiceOrder> select(Page<ServiceOrder> record);

    long count(Page<ServiceOrder> record);

}
