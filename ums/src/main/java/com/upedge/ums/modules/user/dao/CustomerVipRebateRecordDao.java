package com.upedge.ums.modules.user.dao;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerVipRebateRecord;
import com.upedge.ums.modules.user.vo.CustomerVipRebateCountVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author gx
 */
public interface CustomerVipRebateRecordDao {

    BigDecimal selectCustomerTotalVipRebate(Long customerId);

    List<CustomerVipRebateCountVo> selectCustomerCountRebateByDay(Long customerId);

    List<CustomerVipRebateCountVo> selectCustomerCountRebateByMonth(Long customerId);

    CustomerVipRebateRecord selectByOrderIdAndType(@Param("orderId") Long orderId, @Param("rebateType") Integer rebateType);

    CustomerVipRebateRecord selectByPrimaryKey(CustomerVipRebateRecord record);

    int deleteByPrimaryKey(CustomerVipRebateRecord record);

    int updateByPrimaryKey(CustomerVipRebateRecord record);

    int updateByPrimaryKeySelective(CustomerVipRebateRecord record);

    int insert(CustomerVipRebateRecord record);

    int insertSelective(CustomerVipRebateRecord record);

    int insertByBatch(List<CustomerVipRebateRecord> list);

    List<CustomerVipRebateRecord> select(Page<CustomerVipRebateRecord> record);

    long count(Page<CustomerVipRebateRecord> record);

}
