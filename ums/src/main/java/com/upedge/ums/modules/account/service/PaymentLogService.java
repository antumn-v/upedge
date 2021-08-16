package com.upedge.ums.modules.account.service;

import com.upedge.ums.modules.account.entity.PaymentLog;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface PaymentLogService{

    PaymentLog selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(PaymentLog record);

    int updateByPrimaryKeySelective(PaymentLog record);

    int insert(PaymentLog record);

    int insertSelective(PaymentLog record);

    List<PaymentLog> select(Page<PaymentLog> record);

    long count(Page<PaymentLog> record);
}

