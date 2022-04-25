package com.upedge.ums.modules.address.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.address.entity.CustomerAddress;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerAddressDao{

    List<CustomerAddress> selectCustomerNormalAddress(Long customerId);

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
