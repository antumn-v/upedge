package com.upedge.ums.modules.account.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.account.PaypalPayment;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface PaypalPaymentDao{

    BigDecimal selectFixFeeByAmount(BigDecimal amount, BigDecimal percentage);

    PaypalPayment selectByPrimaryKey(Long id);

    PaypalPayment selectByPaymentId(String paymentId);

    int deleteByPrimaryKey(PaypalPayment record);

    int updateByPrimaryKey(PaypalPayment record);

    int updateByPrimaryKeySelective(PaypalPayment record);

    int insert(PaypalPayment record);

    int insertSelective(PaypalPayment record);

    int insertByBatch(List<PaypalPayment> list);

    List<PaypalPayment> select(Page<PaypalPayment> record);

    long count(Page<PaypalPayment> record);

    void paypalUpdateRemark(@Param("id") Long id, @Param("remark") String remark);
}
