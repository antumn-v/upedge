package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.PaymentLog;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface PaymentLogDao{

    PaymentLog selectByPrimaryKey(PaymentLog record);

    int deleteByPrimaryKey(PaymentLog record);

    int updateByPrimaryKey(PaymentLog record);

    int updateByPrimaryKeySelective(PaymentLog record);

    int insert(PaymentLog record);

    int insertSelective(PaymentLog record);

    int insertByBatch(List<PaymentLog> list);

    List<PaymentLog> select(Page<PaymentLog> record);

    long count(Page<PaymentLog> record);

}
