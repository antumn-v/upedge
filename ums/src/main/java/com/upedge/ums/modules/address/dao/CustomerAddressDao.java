package com.upedge.ums.modules.address.dao;

import com.upedge.ums.modules.address.entity.CustomerAddress;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface CustomerAddressDao{

    int cancelOtherDefaultAddress(@Param("id") Long id,
                                  @Param("customerId")Long customerId);

    CustomerAddress selectByPrimaryKey(CustomerAddress record);

    int deleteByPrimaryKey(CustomerAddress record);

    int updateByPrimaryKey(CustomerAddress record);

    int updateByPrimaryKeySelective(CustomerAddress record);

    int insert(CustomerAddress record);

    int insertSelective(CustomerAddress record);

    int insertByBatch(List<CustomerAddress> list);

    List<CustomerAddress> select(Page<CustomerAddress> record);

    long count(Page<CustomerAddress> record);

}
