package com.upedge.ums.modules.account.dao;

import com.upedge.ums.modules.account.entity.PaypalPayment;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface PaypalPaymentDao{

    PaypalPayment selectByPrimaryKey(PaypalPayment record);

    int deleteByPrimaryKey(PaypalPayment record);

    int updateByPrimaryKey(PaypalPayment record);

    int updateByPrimaryKeySelective(PaypalPayment record);

    int insert(PaypalPayment record);

    int insertSelective(PaypalPayment record);

    int insertByBatch(List<PaypalPayment> list);

    List<PaypalPayment> select(Page<PaypalPayment> record);

    long count(Page<PaypalPayment> record);

}
