package com.upedge.sms.modules.center.dao;

import com.upedge.common.base.Page;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author gx
 */
public interface ServiceOrderDao {

    ServiceOrder selectByRelateId(@Param("relateId") Long relateId, @Param("serviceType") Integer serviceType);

    int updateToPaidByRelateId(@Param("id") Long id, @Param("serviceType") Integer serviceType, @Param("payAmount") BigDecimal payAmount, @Param("updateTime") Date updateTime);

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
